package com.john.mybatisspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.mybatisspring.mapper.UserMapper;
import com.john.mybatisspring.model.User;


@Service
public class UserService {
	
	@Autowired UserMapper userMapper;
	
	public User selectById(Integer id){
		return userMapper.selectById(id);
	}
	

}
