package com.john.mybatis;

import org.junit.Test;

import com.john.mybatis.mapper.AddressWithUsersMapper;
import com.john.mybatis.model.AddressWithUsers;

public class AddressWithUsersMapperTest extends BaseTest {
	
	AddressWithUsersMapper mapper = null;
	
	
	@Test
	public void testSelect(){
		mapper = session.getMapper(AddressWithUsersMapper.class);
		AddressWithUsers one = mapper.selectById(1);
		System.out.println(one.toJSONString());
		session.close();
		
		/*
		 *  AddressWithUsersMapper.selectById] - ==>  Preparing: select * from test_address where id = ? 
			AddressWithUsersMapper.selectById] - ==> Parameters: 1(Integer)
			UserMapper.selectByAddrId] - ====>  Preparing: select * from test_user where address_id = ? 
			UserMapper.selectByAddrId] - ====> Parameters: 1(Integer)
			UserMapper.selectByAddrId] - <====      Total: 2
			AddressWithUsersMapper.selectById] - <==      Total: 1
			{"id":1,"name":"address1",
			"users":[{"name":"user1","nickName":"nickName1","createTime":1496587916000,"id":1},
			{"name":"user3","nickName":"nickName3","createTime":1497231006000,"id":3}]}
		*/		
				
				
	}
	
}
