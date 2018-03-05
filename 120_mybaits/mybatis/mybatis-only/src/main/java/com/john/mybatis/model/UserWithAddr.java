package com.john.mybatis.model;

public class UserWithAddr extends User{
	
	
	private static final long serialVersionUID = 1L;

	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
