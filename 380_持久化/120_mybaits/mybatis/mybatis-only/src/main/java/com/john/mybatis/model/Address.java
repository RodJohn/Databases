package com.john.mybatis.model;

import com.john.core.model.SysModel;


public class Address extends SysModel {

	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
