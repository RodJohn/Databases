package com.john.mybatisspring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationTest {
	
	
	public ClassPathXmlApplicationContext context = null;
	
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void Test() {
	}

}
