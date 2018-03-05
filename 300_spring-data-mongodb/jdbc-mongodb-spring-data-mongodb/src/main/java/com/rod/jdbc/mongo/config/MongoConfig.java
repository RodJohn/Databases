/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.rod.jdbc.mongo.config;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;  

@Configuration
public class MongoConfig {
	
	
	@Bean Mongo mongo() throws Exception {
			Mongo mongo = new MongoClient("192.168.221.53");
			return mongo;
		}  
	  	
	 @Bean MongoTemplate mongoTemplate() throws Exception {  
	        return new MongoTemplate(mongo(),"test");  
	    }

}
