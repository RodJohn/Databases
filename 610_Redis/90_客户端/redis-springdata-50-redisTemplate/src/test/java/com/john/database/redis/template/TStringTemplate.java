package com.john.database.redis.template;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.database.redis.MainApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= MainApplication.class)
public class TStringTemplate {

	@Autowired  private StringRedisTemplate  template;
	
	
	BoundValueOperations<String, String> boundValueOps = null;
	
	
	@Before
	public void before() throws Exception {
		boundValueOps = template.boundValueOps("1:1");
	}
	
	
	@Test
	public void upsert() throws Exception {
		boundValueOps.set("1");
		boundValueOps.set("2");
		boundValueOps.set("3");
	}
	
}
