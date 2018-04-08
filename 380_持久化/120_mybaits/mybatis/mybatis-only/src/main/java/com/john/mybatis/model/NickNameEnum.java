package com.john.mybatis.model;

public enum NickNameEnum {
	
	junjun("俊俊",-1),
	lele("乐乐",2),
	;
	
	
	private String description;
	private Integer intValue;
	
	
	private NickNameEnum() {
	}

	private NickNameEnum(String description, Integer intValue) {
		this.description = description;
		this.intValue = intValue;
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
