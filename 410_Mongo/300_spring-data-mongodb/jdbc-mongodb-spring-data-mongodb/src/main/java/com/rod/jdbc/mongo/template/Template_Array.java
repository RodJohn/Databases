/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.rod.jdbc.mongo.template;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.BasicDBObject;
import com.rod.jdbc.mongo.jet.Jet;
import com.rod.jdbc.mongo.jet.Passenger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Template_Array {

	
    @Autowired
    MongoTemplate template;

    
    
    /*TODO 数组查询******************************************************************************/
    @Test public void queryArray() {
    	//通用分页查询
    	Query query = new Query();
    	
    	//数组容量 只有精确 没有大于小于 除非 加入特定数据
//    	Criteria colorWhere = Criteria.where("color");
//    	colorWhere.size(1);
    	
    	//数组包含
    	Criteria colorWhere = Criteria.where("color");
    	colorWhere.is("red").and("color").is("green");
    	
    	//3.6
    	query.addCriteria(colorWhere);
    	List<Jet> result = template.find(query, Jet.class); 
    	System.out.println(result);
    }
    
    
    
    /*TODO 简单数组追加******************************************************************************/
    @Test public void addArray() {
    	Query query = new Query(Criteria.where("_id").is("584a63c36252302f096eecc7"));
        Update update = new Update();
        update.addToSet("color", "blue");
        //如果已经存在了 不会被继续添加  默认eque
        template.updateFirst(query, update, Jet.class);
    }
    
    
    /*TODO 简单数组成员更新******************************************************************************/
    @Test public void updateArray() {
    	Query query = new Query(
    			Criteria.where("_id").is("584a63c36252302f096eecc7").and("color").is("blue"));
         Update update = new Update();
         update.set("color.$", "red");
         //如果对象数组 有多个name=lijun 只有第一个被修改
    	 template.updateFirst(query, update, Jet.class);
    }
    
    
    
    /*TODO 简单数组成员删除******************************************************************************/
    @Test public void deleteArray() {
    	Query query = new Query(
    			Criteria.where("_id").is("584a63c36252302f096eecc7"));
    	Update update = new Update();
    	//update.unset("color.$");//null 不好
    	update.pull("color", "gray");//移除===gray的
    	template.updateFirst(query, update, Jet.class);
    }
    
    
    
    
    /*TODO 对象数组添加******************************************************************************/
    @Test public void addArray1() {
    	Query query = new Query(Criteria.where("_id").is("584a63c36252302f096eecc7"));
        Update update = new Update();
        update.addToSet("passengers", new Passenger("lijun", 26));
        //如果已经存在了 不会被继续添加  默认eque
        template.updateFirst(query, update, Jet.class);
    } 
    
    /*TODO 简单数组成员更新******************************************************************************/
    @Test public void updateArray1() {
    	Query query = new Query(
    			Criteria.where("_id").is("584a63c36252302f096eecc7").and("passengers.name").is("lijun"));
    	Update update = new Update();
    	update.set("passengers.$.age", 28);
    	//如果对象数组 有多个name=lijun 只有第一个被修改
    	template.updateMulti(query, update, Jet.class);
    }
    
    
    
    /*TODO 简单数组成员删除******************************************************************************/
    @Test public void deleteArray1() {
    	Query query = new Query(
    			Criteria.where("_id").is("584a63c36252302f096eecc7"));
    	Update update = new Update();
    	update.pull("passengers", new BasicDBObject("age", 28)); 
    	//update({ },{ $pull: { passengers: { age:26 } } },{ multi: true })
//    	update.unset("passengers.$");		//可以结果weinull
    	template.updateFirst(query, update, Jet.class);
    }
}
