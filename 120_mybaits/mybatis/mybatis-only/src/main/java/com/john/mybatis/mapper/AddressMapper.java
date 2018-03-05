package com.john.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.john.mybatis.model.Address;

public interface AddressMapper {
	
	Address selectById(@Param("id") Integer id);

}
