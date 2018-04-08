package com.john.mybatis.cache;

import java.io.IOException;

import org.junit.Test;

import com.john.mybatis.BaseTest;
import com.john.mybatis.mapper.UserMapper;
import com.john.mybatis.model.User;

public class CacheLocalDirtyTest extends BaseTest {
	
	
	@Test
	/**
	 * 测试SQLsession级别的一级缓存的脏数据
	 */
	/**
	 * 命中缓存以后,返回的是缓存的引用,
	 */
	public void testCache1() throws IOException{
		UserMapper userMapper = session.getMapper(UserMapper.class);
		User user1 = userMapper.selectById(2);
		user1.setName("被修改的名字");
		User user2 = userMapper.selectById(2);
		System.out.println(user1.equals(user2));//true
	}
//	{-430448472:-1667831:com.john.mybatis.mapper.UserMapper.selectById:0:2147483647:select * from test_user where id = ?:2=[{"name":"被修改的名字","nickName":"nickName2","createTime":1497516551000,"id":2}]}
	//
}
