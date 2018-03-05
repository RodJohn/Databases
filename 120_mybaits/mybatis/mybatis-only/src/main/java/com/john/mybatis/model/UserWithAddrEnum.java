package com.john.mybatis.model;

public class UserWithAddrEnum extends User{
	
	
	private static final long serialVersionUID = 1L;

	private AddressEnum address;

	public AddressEnum getAddress() {
		return address;
	}

	public void setAddress(AddressEnum address) {
		this.address = address;
	}

}