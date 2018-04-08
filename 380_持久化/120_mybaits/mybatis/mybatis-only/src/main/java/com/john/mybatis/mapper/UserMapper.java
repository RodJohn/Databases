package com.john.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.john.mybatis.model.User;

public interface UserMapper {
	
	List<User> selectAll();

	void insertBatch(@Param("ids") List<Integer> ids);
	
	List<User> selectByAddrId(@Param("id") Integer id);
	
	User selectById(@Param("id") Integer id);

}
