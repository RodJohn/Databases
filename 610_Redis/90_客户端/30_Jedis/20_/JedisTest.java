package com.jedis.sample;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.tests.utils.ClientKillerUtil;
import redis.clients.util.SafeEncoder;

public class JedisTest extends JedisCommandTestBase {
	private String serverIps = "192.168.1.128";

	@Test
	public void useWithoutConnecting() {
		Jedis jedis = new Jedis(serverIps);
		jedis.auth("foobared");
		jedis.dbSize();
	}

	@Test
	public void checkBinaryData() {
		byte[] bigdata = new byte[1777];
		for (int b = 0; b < bigdata.length; b++) {
			bigdata[b] = (byte) ((byte) b % 255);
		}
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("data", SafeEncoder.encode(bigdata));

		String status = jedis.hmset("foo", hash);
		assertEquals("OK", status);
		assertEquals(hash, jedis.hgetAll("foo"));
	}

	@Test
	public void connectWithShardInfo() {
		JedisShardInfo shardInfo = new JedisShardInfo(serverIps,
				Protocol.DEFAULT_PORT);
		shardInfo.setPassword("foobared");
		Jedis jedis = new Jedis(shardInfo);
		jedis.get("foo");
	}
	
	 @Test
	  public void testAvoidLeaksUponDisconnect() throws InterruptedException {
	    List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(2);
	    // 6379
	    JedisShardInfo shard1 = new JedisShardInfo("192.168.1.128", 6379);
	    shard1.setPassword("foobared");
	    shards.add(shard1);
	    // 6380
	    JedisShardInfo shard2 = new JedisShardInfo("192.168.1.128", 6380);
	    shard2.setPassword("foobared");
	    shards.add(shard2);

	    @SuppressWarnings("resource")
	    ShardedJedis shardedJedis = new ShardedJedis(shards);
	    // establish the connection for two redis servers
	    shardedJedis.set("a", "bar");
	    JedisShardInfo ak = shardedJedis.getShardInfo("a");
	    assertEquals(shard2, ak);
	    shardedJedis.set("b", "bar1");
	    JedisShardInfo bk = shardedJedis.getShardInfo("b");
	    assertEquals(shard1, bk);

	    // We set a name to the instance so it's easy to find it
	    Iterator<Jedis> it = shardedJedis.getAllShards().iterator();
	    Jedis deadClient = it.next();
	    deadClient.clientSetname("DEAD");

	    ClientKillerUtil.killClient(deadClient, "DEAD");

	    assertEquals(true, deadClient.isConnected());
	    assertEquals(false, deadClient.getClient().getSocket().isClosed());
	    assertEquals(false, deadClient.getClient().isBroken()); // normal - not found

	    shardedJedis.disconnect();

	    assertEquals(false, deadClient.isConnected());
	    assertEquals(true, deadClient.getClient().getSocket().isClosed());
	    assertEquals(true, deadClient.getClient().isBroken());

	    Jedis jedis2 = it.next();
	    assertEquals(false, jedis2.isConnected());
	    assertEquals(true, jedis2.getClient().getSocket().isClosed());
	    assertEquals(false, jedis2.getClient().isBroken());

	  }

	@Test(expected = JedisConnectionException.class)
	public void timeoutConnection() throws Exception {
		jedis = new Jedis(serverIps, 6379, 15000);
		jedis.select(0);
		// jedis.auth("foobared");
		jedis.configSet("timeout", "1");
		Thread.sleep(2000);
		jedis.hmget("foobar", "foo");
	}

	@Test(expected = SocketTimeoutException.class)
	public void readTimeout() throws Exception {
		jedis = new Jedis(new URI("redis://192.168.1.128:6379/0"), 15000, 1);
		Thread.sleep(10000);// 先执行，然后把防火墙关闭，导致socket不能读取数据
		// jedis = new Jedis(serverIps, 6379, 15000);
		// jedis.auth("foobared");
		// jedis.configSet("timeout", "1");
		// Thread.sleep(2000);
		jedis.hmget("foobar1", "foo");
	}

	@Test
	public void setDataWithTtl() throws Exception {
		String res = jedis.setex("1", 5, "11");
		assertEquals("OK", res);
		String get = jedis.get("1");
		assertEquals("11", get);
		Thread.sleep(6000);
		get = jedis.get("1");
		assertNull(get);
	}

	@Test
	public void scanKey() {
		jedis.set("11", "11data");
		jedis.set("12", "12data");
		jedis.set("13", "13data");
		jedis.set("14", "14data");
		ScanParams sp = new ScanParams();
		sp.match("*");
		sp.count(5);
		ScanResult<String> rres = jedis.scan("0", sp);
		System.out.println("---cursor-" + rres.getCursor());
		for (String s : rres.getResult()) {
			System.out.println(s);
		}
		assertNotNull(rres.getResult());
		assertEquals(rres.getResult().size(), 4);
		// assertTrue(rres.getResult().size()0);
	}

	@Test
	public void checkkey() {
		jedis.set("11", "11data");
		assertTrue(jedis.exists("11".getBytes()));
		assertFalse(jedis.exists("12".getBytes()));
	}

	@Test
	public void getttl() throws InterruptedException {
		jedis.setex("1", 50, "11");
		Thread.sleep(1000);
		System.out.println("key 1的超时时间还剩:" + jedis.ttl("1"));
		assertTrue(jedis.ttl("1") < 50);
	}

	@Test(expected = JedisDataException.class)
	public void incrInteger() {
		jedis.set("1", "1");
		long res = jedis.incr("1");
		assertEquals(res, 2);
		jedis.set("2", "1.1");
		jedis.incrBy("2", 1);// 对小数进行整数操作
	}

	@Test
	public void subStr() throws UnsupportedEncodingException {
		jedis.set("1", "测试截取字符串的字串");
		System.out.println("测试截取字符串的字串".getBytes().length);
		System.out.println(jedis.strlen("1"));
		String res = jedis.getrange("1", 3, 8);// 这个位置对于中文来说不是一对一的，容易出现乱码
		System.out.println(res);
		assertEquals(res, "试截取");
	}

	@Test
	public void setMoreAndGetMore() {
		jedis.mset("1", "1data", "2", "2data", "3", "3data");
		assertEquals(jedis.get("1"), "1data");
		assertEquals(jedis.get("2"), "2data");
		assertEquals(jedis.get("3"), "3data");
		List<String> res = jedis.mget("1", "2", "3");
		assertEquals(res.size(), 3);
		for (String s : res) {
			System.out.println(s);
		}
	}

	@Test
	public void pushAndpop() {
		jedis.rpush("1", "11", "22");
		long listsize = jedis.llen("1");
		assertEquals(listsize, 2l);
		String first = jedis.rpop("1");
		assertEquals(first, "22");
	}

	@Test
	public void rpoplpush(){
		jedis.rpush("1", "11", "22");
		jedis.rpoplpush("1","2");
		jedis.rpoplpush("1","2");
		String first = jedis.lpop("2");
		long listsize = jedis.llen("1");
		assertEquals(listsize, 0);		
		assertEquals(first, "11");
	}
	
	
	@Test
	public void lrangeAndltrim(){
		jedis.rpush("1", "11", "22","33","44","55");
		List<String> res = jedis.lrange("1", 0, 1);
		assertEquals(res.size(),2);
		assertEquals(res.get(0),"11");
		jedis.ltrim("1", -3, -1);
		String indexval = jedis.lindex("1", 0);
		assertEquals(indexval,"33");
	}
	
	@Test(expected = JedisDataException.class)
	public void errorDataType(){
		jedis.set("1", "1");
		jedis.rpop("1");//操作在错误的数据结构上
	}
	
	@Test
	public void setBaseOpt(){
		jedis.sadd("1","22","33","22","44","55","66");
		long size = jedis.scard("1");
		assertEquals(size,5);
		jedis.srem("1", "22");
		assertFalse(jedis.sismember("1", "22"));
	}
	
	//求交集
	@Test
	public void sinter(){
		jedis.sadd("1","22","33","22","44","55","66");
		jedis.sadd("2","22","77","44","55","88");
		Set<String> res  = jedis.sinter("1","2");
		assertTrue(res.contains("22"));
		assertTrue(res.contains("44"));
		assertTrue(res.contains("55"));
	}
	
	@Test
	public void sortedSetOpt(){
		Map<String,Double> members = new HashMap<String,Double>();
		members.put("11", 10d);
		members.put("22", 9d);
		members.put("33", 14d);
		members.put("44", 6d);
		members.put("55", 50d);
		jedis.zadd("1", members);
		Set<String> rangedatas = jedis.zrange("1", 0, 1);
		assertTrue(rangedatas.contains("44"));
		jedis.zadd("1", 40d, "44");
		Set<String> rangedatas1 = jedis.zrange("1", 0, 1);
		assertFalse(rangedatas1.contains("44"));	
		Set<String> datasbyScore = jedis.zrangeByScore("1", 40, 50);
		assertEquals(datasbyScore.size(),2);//44和55
		assertTrue(datasbyScore.contains("44"));
		assertTrue(datasbyScore.contains("55"));
		//for(String s )
	}
	
	@Test
	public void hashOpt(){
		Map<String,String> user = new HashMap<String,String>();
		user.put("id", "111");
		user.put("name", "liubx");
		user.put("money", "10000");
		user.put("other", "other");
		String opres = jedis.hmset("111", user);
		assertEquals(opres,"OK");
		
		Map<String,String> userget = jedis.hgetAll("111");
		assertEquals(userget.get("name"),"liubx");
		jedis.hincrBy("111", "money", 100);
		String money = jedis.hget("111", "money");
		assertEquals(Long.parseLong(money),10100l);
		
		assertEquals(jedis.hget("111", "other"),"other");
		jedis.hdel("111", "other");
		assertNull(jedis.hget("111", "other"));
	}
	
	@Test
	public void transaction(){
		//jedis.watch("1");
		Transaction tx = jedis.multi();
		tx.set("111", "10");
		tx.incr("111");
		tx.set("222", "222value");
		tx.exec();
		assertEquals(Integer.parseInt(jedis.get("111")),11);
	}
	
	@Test
	public void transWithWatch() throws InterruptedException{
		jedis.set("watchkey", "1");
		jedis.watch("watchkey");
		Transaction tx = jedis.multi();
		tx.set("111", "10");
		tx.incr("111");
		tx.set("222", "222value");
		Thread.sleep(10000);
		tx.exec();
		assertNotEquals(Integer.parseInt(jedis.get("111")),11);		
	}
	
	@Test(expected = JedisDataException.class)
	public void failWhenSendingNullValues() {
		jedis.set("foo", null);
	}

	@Test
	public void checkCloseable() {
		jedis.close();//本质是做输入流 输出流 socket的关闭
		BinaryJedis bj = new BinaryJedis(serverIps);
		bj.connect();//发起socket连接
		bj.close();
	}

	@Test
	public void checkDisconnectOnQuit() {
		jedis.quit();//向服务器发送quit命令，本地客户端关闭socket
		assertFalse(jedis.getClient().isConnected());
	}
	  @Test
	  public void checkConnections() {
	    //JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
	    JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
	    Jedis jedis = pool.getResource();
	   // jedis.auth("foobared");
	    jedis.set("foo", "bar");
	    assertEquals("bar", jedis.get("foo"));
	    jedis.close();
	    pool.destroy();
	    assertTrue(pool.isClosed());
	  }
}