package com.rod.jdbc.mongo.jet;

import java.util.Date;
import java.util.List;

public class Engine {
	
	private Integer id;		
	private String name;
	private List<Date> historys;    //历史使用表
	
	
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
	public List<Date> getHistorys() {
		return historys;
	}
	public void setHistorys(List<Date> historys) {
		this.historys = historys;
	}
	
}
