package com.john.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.john.mybatis.model.AddressWithUsers;

public interface AddressWithUsersMapper {
	
	AddressWithUsers selectById(@Param("id") Integer id);

}
