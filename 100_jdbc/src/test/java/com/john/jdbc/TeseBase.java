package com.john.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Test;

public class TeseBase {

	
	public Connection con = null;
	
	
	@Before
	public void before(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://120.76.85.35:13307/linklaws_dev", 
					"linklaws", 
					"linklaws_123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test() {
	}
	
	
}
