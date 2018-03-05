package com.john.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.john.mybatis.mapper.UserMapper;
import com.john.mybatis.model.User;

public class UserMapperTest {
	
	SqlSession session = null;
	UserMapper userMapper = null;
	
	@Before
	public void before() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader);
		session = ssf.openSession();
		userMapper = session.getMapper(UserMapper.class);
	}
	
	
	@Test
	public void test(){
		session.close();
		
	}
	@Test
	public void testSelect(){
		UserMapper userMapper = session.getMapper(UserMapper.class);
		List<User> users = userMapper.selectAll();
		System.out.println(users.get(0).toJSONString());
		// 没有映射  {"name":"name","nickName":null,"createTime":null,"id":1}
		// 使用  		{"name":"name","nickName":"nickName1","createTime":1496587916000,"id":1}
		session.close();
		
	}
	
	@Test
	public void testForeach(){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(2);
		ids.add(3);
		ids.add(4);
		userMapper.insertBatch(ids);
	}

	
	@After
	public void after() {
		session.close();
	}
}
