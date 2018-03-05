package com.john.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BaseTest {
	
	public SqlSession session = null;
	public SqlSessionFactory ssf =  null;
	
	@Before
	public void before() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		ssf = new SqlSessionFactoryBuilder().build(reader);
		session = ssf.openSession();
	}
	
	
	@Test
	public void test(){
		
	}

	
	@After
	public void after() {
		session.close();
	}
}
