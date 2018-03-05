package com.john.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.john.mybatis.model.AddressEnum;
import com.john.mybatis.model.NickNameEnum;
import com.john.mybatis.model.User;
import com.john.mybatis.model.UserWithAddrEnum;

public interface UserWithAddrMapper {
	
	List<User> selectAllUsersWithAddr();
	
	void updateOne();
	
	void updateUseEnumNameById(@Param("nickName") NickNameEnum nickName , @Param("id") Integer id);
	
	void updateUseAddressById(@Param("address") AddressEnum addressEnum , @Param("id") Integer id);
	
	UserWithAddrEnum findById(@Param("id") Integer id);
	
}
