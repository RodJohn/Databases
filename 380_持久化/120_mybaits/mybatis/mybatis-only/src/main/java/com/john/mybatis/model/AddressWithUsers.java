package com.john.mybatis.model;

import java.util.List;


public class AddressWithUsers extends Address {

	
	private static final long serialVersionUID = 1L;
	
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
}
