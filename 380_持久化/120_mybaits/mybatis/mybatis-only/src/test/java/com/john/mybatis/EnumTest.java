package com.john.mybatis;

import org.junit.Test;

import com.john.mybatis.mapper.UserWithAddrMapper;
import com.john.mybatis.model.AddressEnum;
import com.john.mybatis.model.NickNameEnum;
import com.john.mybatis.model.UserWithAddrEnum;

public class EnumTest extends BaseTest {
	
	UserWithAddrMapper userWithAddrMapper = null;
	
	
	@Test
	//使用默认的枚举name
	public void testName(){
		userWithAddrMapper = session.getMapper(UserWithAddrMapper.class);
		userWithAddrMapper.updateUseEnumNameById(NickNameEnum.junjun, 3);
		session.commit();
	}
	
	@Test
	//特定类 使用的枚举的index
	public void testIndex(){
		userWithAddrMapper = session.getMapper(UserWithAddrMapper.class);
		userWithAddrMapper.updateUseAddressById(AddressEnum.beijin, 2);
		session.commit();
	}
	
	
	
	@Test
	//特定类 使用自定义的typehandle
	public void testMyTypeHandleSet(){
		userWithAddrMapper = session.getMapper(UserWithAddrMapper.class);
		userWithAddrMapper.updateUseAddressById(AddressEnum.beijin, 2);
		session.commit();
	}
	
	@Test
	//特定类 使用自定义的typehandle
	public void testMyTypeHandleRead(){
		userWithAddrMapper = session.getMapper(UserWithAddrMapper.class);
		UserWithAddrEnum one = userWithAddrMapper.findById(2);
		System.out.println(one);
	}
	
}
