package com.john.mybatis.cache;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.john.mybatis.BaseTest;
import com.john.mybatis.mapper.UserMapper;
import com.john.mybatis.model.User;

public class CacheNamespaceTest extends BaseTest {
	
	
	@Test
	@SuppressWarnings("unused")
	public void testCache1() throws IOException{
		//
		SqlSession session1 = ssf.openSession();
		UserMapper userMapper1 = session1.getMapper(UserMapper.class);
		User user1 = userMapper1.selectById(2);
		User user12 = userMapper1.selectById(2);
		session1.close();
		//
		System.out.println("第二个session");
		SqlSession session2 = ssf.openSession();
		UserMapper userMapper2 = session2.getMapper(UserMapper.class);
		User user2 = userMapper2.selectById(2);
	}
	

}
