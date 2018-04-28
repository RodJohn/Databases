package com.first;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class PipelineTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int count = 1000;
//		long start = System.currentTimeMillis();
//		withoutPipeline(count);
//		long end = System.currentTimeMillis();
//		System.out.println("withoutPipeline: " + (end - start));
//		start = System.currentTimeMillis();
//		usePipeline(count);
//		end = System.currentTimeMillis();
//		System.out.println("usePipeline: " + (end - start));
		
		//pipelineTra();
		pipelineGetRes();
	}

	private static void withoutPipeline(int count) {
		Jedis jr = null;
		try {
			jr = new Jedis("192.168.1.128", 6379);
			for (int i = 0; i < count; i++) {
				jr.incr("testKey1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jr != null) {
				jr.disconnect();
			}
		}
	}

	private static void usePipeline(int count) {
		Jedis jr = null;
		try {
			jr = new Jedis("192.168.1.128", 6379);
			Pipeline pl = jr.pipelined();
			for (int i = 0; i < count; i++) {
				pl.incr("testKey2");
			}
			pl.sync();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jr != null) {
				jr.disconnect();
			}

		}

	}
	
	static void pipelineTra(){
		Jedis jr = null;
		try {
			jr = new Jedis("192.168.1.128", 6379);
			Pipeline pl = jr.pipelined();
			pl.multi();
			Response<String> setres = pl.set("66", "33");
			pl.set("77", "44");
			pl.exec();
			Response<String> getres = pl.get("66");
			pl.sync();
			System.out.println(getres.get());
			System.out.println(setres.get());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jr != null) {
				jr.disconnect();
			}

		}
		
	}
	static void pipelineGetRes(){
		Jedis jr = null;
		try {
			jr = new Jedis("192.168.1.128", 6379);
			Pipeline pl = jr.pipelined();
			pl.set("88", "88");
			Response<String> res = pl.get("88");//没有调用sync之前，取不到结果
			System.out.println(res.get());
			Thread.sleep(1000);
			List<Object> ress = pl.syncAndReturnAll();
			System.out.println((String)ress.get(0));
			System.out.println((String)ress.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jr != null) {
				jr.disconnect();
			}
		}
		
	}
		
}
