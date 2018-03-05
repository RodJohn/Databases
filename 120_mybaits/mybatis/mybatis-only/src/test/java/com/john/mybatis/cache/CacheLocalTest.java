package com.john.mybatis.cache;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.john.mybatis.BaseTest;
import com.john.mybatis.mapper.UserWithAddrMapper;
import com.john.mybatis.model.User;

public class CacheLocalTest extends BaseTest {
	
	
	@Test
	@SuppressWarnings("unused")
	// localCacheScope = session
	// 缓存存放在preparedcache中
	// 相同SQLsession 相同cachekey 读取缓存
	public void testCache1() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory factory = ssf = new SqlSessionFactoryBuilder().build(reader);
		UserWithAddrMapper mapper = factory.openSession().getMapper(UserWithAddrMapper.class);
		List<User> list = mapper.selectAllUsersWithAddr();
		List<User> list1 = mapper.selectAllUsersWithAddr();
	}
	
	@Test
	@SuppressWarnings("unused")
	// 不同的sqlsession将缓存不共用
	public void testCache2() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory factory = ssf = new SqlSessionFactoryBuilder().build(reader);
		List<User> list = factory.openSession().getMapper(UserWithAddrMapper.class).selectAllUsersWithAddr();
		List<User> list1 = factory.openSession().getMapper(UserWithAddrMapper.class).selectAllUsersWithAddr();
	}
	
	@Test
	@SuppressWarnings("unused")
	// 缓存被清空
	public void testCacheClear() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory factory = ssf = new SqlSessionFactoryBuilder().build(reader);
		UserWithAddrMapper mapper = factory.openSession().getMapper(UserWithAddrMapper.class);
		List<User> list = mapper.selectAllUsersWithAddr();
		mapper.updateOne();
		List<User> list1 = mapper.selectAllUsersWithAddr();
	}
}
