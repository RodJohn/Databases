package com.john.mybatis.model;

public enum AddressEnum {
	
	beijin("北京",-1),
	shenzhen("深圳",2),
	;
	
	
	private String description;
	private Integer intValue;
	
	
	private AddressEnum() {
	}

	private AddressEnum(String description, Integer intValue) {
		this.description = description;
		this.intValue = intValue;
	}

	public static AddressEnum valueOf(Integer intValue){
		for (AddressEnum addressEnum : AddressEnum.values()) {
			if(intValue == addressEnum.intValue){
				return addressEnum;
			}
		}
		return null;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}
	
	
	
}
