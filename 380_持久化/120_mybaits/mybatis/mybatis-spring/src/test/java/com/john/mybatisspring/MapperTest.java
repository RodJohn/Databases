package com.john.mybatisspring;

import org.junit.Test;

import com.john.mybatisspring.mapper.UserMapper;
import com.john.mybatisspring.model.User;

public class MapperTest extends ApplicationTest {
	
	
	@Test
	/**
	 * 手动加载获取bean
	 */
	public void Test() {
		UserMapper userMapper = (UserMapper)context.getBean("userMapper");
		User user = userMapper.selectById(2);
		System.out.println(user);
	}

}
