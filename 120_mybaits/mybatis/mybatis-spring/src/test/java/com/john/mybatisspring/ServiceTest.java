package com.john.mybatisspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.mybatisspring.model.User;
import com.john.mybatisspring.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="/applicationContext.xml")  
public class ServiceTest  {
	
	
    @Autowired  
    UserService userService;   
	
	@Test
	public void Test() {
		User user = userService.selectById(2);
		System.out.println(user);
	}

}
