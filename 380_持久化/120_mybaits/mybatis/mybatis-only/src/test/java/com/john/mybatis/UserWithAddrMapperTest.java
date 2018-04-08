package com.john.mybatis;

import java.util.List;

import org.junit.Test;

import com.john.mybatis.mapper.UserWithAddrMapper;
import com.john.mybatis.model.User;

public class UserWithAddrMapperTest extends BaseTest {
	
	UserWithAddrMapper userWithAddrMapper = null;
	
	
	@Test
	@SuppressWarnings("unused")
	public void testSelect(){
		userWithAddrMapper = session.getMapper(UserWithAddrMapper.class);
		List<User> users = userWithAddrMapper.selectAllUsersWithAddr();
		System.out.println(users.get(0).getName());
//		System.out.println(users.get(0).toJSONString());
		//System.out.println(users.get(1).toJSONString());
		session.close();
		
		/*
		UserWithAddrMapper.selectAllUsersWithAddr] - ==>  Preparing: select * from test_user 
		UserWithAddrMapper.selectAllUsersWithAddr] - ==> Parameters: 
		AddressMapper.selectById] - ====>  Preparing: select * from test_address where id = ? 
		AddressMapper.selectById] - ====> Parameters: 1(Integer)
		AddressMapper.selectById] - <====      Total: 1
		AddressMapper.selectById] - ====>  Preparing: select * from test_address where id = ? 
		AddressMapper.selectById] - ====> Parameters: 2(Integer)
		AddressMapper.selectById] - <====      Total: 1
		UserWithAddrMapper.selectAllUsersWithAddr] - <==      Total: 2
		{"name":"user1","nickName":null,"createTime":null,"address":{"id":1,"name":"address1"},"id":1}
		*/	
		
		//debug的时候会自动去查询
				
				
	}
	
}
