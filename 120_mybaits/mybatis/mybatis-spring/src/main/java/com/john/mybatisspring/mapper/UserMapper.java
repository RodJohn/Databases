package com.john.mybatisspring.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.john.mybatisspring.model.User;

public interface UserMapper {
	
	@Select(" select * from test_user where id = #{id} ")
	User selectById(@Param("id") Integer id);

}
