package com.john.database.redis.template.templatetype;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.database.redis.MainApplication;
import com.john.database.redis.bean.User;



/**
 * 操作没有修改数量返回
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= MainApplication.class)
public class TestUserTemplate {
	
	@Autowired private RedisTemplate<String, User> redisTemplate;
	
	private ValueOperations<String, User> opsForValue = null;
	
	@Before
	public void before() {
		opsForValue = redisTemplate.opsForValue();
    }
	
	
	
	@Test
	public void upsert() throws Exception {
		User user = new User("超人", 20);
		opsForValue.set(user.getUsername(), user);
    }
	
	
	@Test
	public void append() throws Exception {
		User user = opsForValue.get("超人");
		System.out.println();
	}

	
	
	


	
	
}
