package com.john.database.redis.template.templatetype;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.database.redis.MainApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= MainApplication.class)

public class TestBoundOps {
	
	
	@Autowired private StringRedisTemplate stringRedisTemplate;
	
	private BoundValueOperations<String, String> boundValueOps = null;
	
	@Before
	public void before() {
		boundValueOps = stringRedisTemplate.boundValueOps("boundValueOps");
    }
	
	@Test
	public void upsert() throws Exception {
		System.out.println(boundValueOps.get());
		boundValueOps.set("111");
		System.out.println(boundValueOps.get());
		boundValueOps.set("222");
		System.out.println(boundValueOps.get());
		boundValueOps.append("333");
		System.out.println(boundValueOps.get());
		boundValueOps.set("0",0);
		System.out.println(boundValueOps.get());
		boundValueOps.set("9",9);
		System.out.println(boundValueOps.get());
//		022
//		111
//		222
//		222333
//		022333
//		022333
    }
	
	
	@Test
	public void get() throws Exception {
		System.out.println(boundValueOps.get());
		boundValueOps.set("111");
		System.out.println(boundValueOps.get(0,1));
		System.out.println(boundValueOps.getAndSet("getAndSet"));
		System.out.println(boundValueOps.get());
//		getAndSet
//		11
//		111
//		getAndSet
	}
	
	
	@Test
	public void expire() throws Exception {
		//
		boundValueOps.expire(2, TimeUnit.MINUTES);
		System.out.println(stringRedisTemplate.getExpire("Taaa"));
		boundValueOps.persist();
	}
	
	
	@Test
	public void delete() throws Exception {
		//
		stringRedisTemplate.delete("Taaa");
	}

	
	
}
