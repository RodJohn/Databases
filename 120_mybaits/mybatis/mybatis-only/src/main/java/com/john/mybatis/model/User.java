package com.john.mybatis.model;

import java.util.Date;

import com.john.core.model.SysModel;

public class User extends SysModel{
	
	
	private static final long serialVersionUID = 1L;

	private Integer Id;
	
	private String name;

	private String nickName;
	
	private Date createTime;
	
	

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
