package com.john.database.redis.template.redistype;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.database.redis.MainApplication;



/**
 * 操作没有修改数量返回
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= MainApplication.class)
public class TestOpsForList {
	
	
 	@Autowired
 	private RedisTemplate<String, String> redisTemplate;
	
	
	//绑定/非绑定key的ops
	private BoundListOperations<String, String> ttListBoundOps = null;
	private static String ttKey = "TTOps";
	
	@Before
	public void before() {
		ttListBoundOps = redisTemplate.boundListOps(ttKey);
    }
	
	
	@Test
	public void upsert() throws Exception {
		ttListBoundOps.leftPush("left1");
		
    }
//	@Test
//	public void append() throws Exception {
//		//set 存在追加，不存在新增
//		ttOps.append("222");
//		ttOps.append("Taa1");
//	}
//
//	@Test
//	public void delete() throws Exception {
//		//
//		redisTemplate.delete("Taaa");
//	}
//	
//	@Test
//	public void expire() throws Exception {
//		//默认存活时间-1
//		//修改存活时间
//		redisTemplate.expire("Taaa", 2, TimeUnit.MINUTES);
//		System.out.println(redisTemplate.getExpire("Taaa"));
//		//持久化存活时间为-1
//		redisTemplate.persist("Taaa");
//	}
//	
//	
//	@Test
//	//获取值
//	public void query() throws Exception {
//		System.out.println(ttOps.get());
//		System.out.println(ttOps.getAndSet("123"));
//		System.out.println(ttOps.get(0, 1));
//	}
	
	
	/**
	 * 非绑定提供的批量操作
	@Test
	public void batch() throws Exception {
		//set
		Map<String,String> batchMap = new HashMap<>();
		batchMap.put("key1", "value1");
		batchMap.put(ttKey, "value2");
		ops.multiSet(batchMap);
		//get
		List<String> keys = new ArrayList<>();
		keys.add(ttKey);
		keys.add("key1");
		ops.multiGet(keys);
		System.out.println();
	}
	 */


	
	
}
