/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.rod.jdbc.mongo.template;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.rod.jdbc.mongo.jet.Jet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Template_R {

	
    @Autowired
    MongoTemplate template;

    Jet j10, su27,y20;
    ArrayList<Jet> jets = new ArrayList<Jet>();
    ArrayList<String> color = new ArrayList<String>();
    
    @Before
    public void before() {
    	j10 = new Jet("j10",2700);
    	su27 = new Jet("su27",2900);
    	y20 = new Jet("y20",900);
    	jets.add(j10);
    	jets.add(su27);
    	jets.add(y20);
    	color.add("red");
    	color.add("blue");	
    }

    
    
    
    /**TODO 逻辑条件**************************************************************************************/
    @Test public void queryOriginBasic() {
    	Query query = new  Query();
    	query.addCriteria(Criteria.where("id").exists(true)
    			.norOperator(
    					Criteria.where("speed").is(2700),
    					Criteria.where("level").is(1)));
    	//Query: { "id" : { "$exists" : true} , "$or" : [ { "speed" : 2700} , { "level" : 1}]}, Fields: null, Sort: null
    	//Query: { "id" : { "$exists" : true} , "$and" : [ { "speed" : 2700} , { "level" : 1}]}, Fields: null, Sort: null
    	//Query: { "id" : { "$exists" : true} , "$nor" : [ { "speed" : 2700} , { "level" : 1}]}, Fields: null, Sort: null
    	List<Jet> result = template.find(query, Jet.class); 
    	System.out.println(result);
    }
    
    
    
    /*TODO 查询字段不存在/为null/长度为零************************************************/
    @Test public void queryBlank() {
    	Query query = new  Query();
    	query.addCriteria(new Criteria()
    			.orOperator(
    					Criteria.where("color").exists(false),
    					Criteria.where("color").is(null),
    					Criteria.where("color").size(0)));
    	List<Jet> result = template.find(query, Jet.class); 
    	//{ "$or" : [ { "color" : { "$exists" : false}} , { "color" :  null } , { "color" : { "$size" : 0}}]}
    	System.out.println(result);
    }
    
    
    
    /*TODO 数组查询******************************************************************************/
    @Test public void queryArray() {
    	//3.通用分页查询
    	Query query = new Query();
		
    	
    	//3.3.3数组容量
    	/*Criteria colorWhere = Criteria.where("color");
    	colorWhere.size(1);*/
    	
    	//数组包含
    	Criteria colorWhere = Criteria.where("color");
    	colorWhere.is("red").andOperator(Criteria.where("color").is("green"));
    	
    	//3.6
    	query.addCriteria(colorWhere);
    	List<Jet> result = template.find(query, Jet.class); 
    	System.out.println(result);
    }
    
    
    /*TODO 通用简单查询******************************************************************************/
    @Test public void query() {
    	//3.通用分页查询
    	Query query = new Query();
		
		//注意条件的type
    	//3.3.1相等条件
    	Criteria speedWhere = Criteria.where("speed");
    	speedWhere.is(2700);
    	speedWhere.type(1);
    	Integer[] speeds = {2710,2720};
    	speedWhere.in(speeds);
    	
    	//3.3.2正则表达式
    	Criteria countryWhere = Criteria.where("country");
    	countryWhere.regex("");
    	
    	//3.3.3数组
    	Criteria colorWhere = Criteria.where("color");
    	colorWhere.size(2);
    	
    	//3.6
    	query.addCriteria(colorWhere).addCriteria(colorWhere.orOperator(colorWhere));
    	
    }
    
    
    
    /*TODO 万能条件查询******************************************************************************/
    @Test public void queryKing() {
    	Query query = new Query();
    	Criteria speedWhere = Criteria.where("speed");
    	
    	
    }
    
    
    
    /*TODO 统计查询******************************************************************************/
    @Test public void queryReport() {
    	Query query = new Query();
    	Criteria speedWhere = Criteria.where("speed");
    	query.addCriteria(speedWhere);

    	//排序
		query.with(new Sort("name").and(new Sort(Direction.DESC,"speed")));
		//字段选择
    	Field fields = query.fields();
    	fields.exclude("speed");
    	fields.include("level");   
    	//分页
    	query.skip(1);
    	query.limit(2);
    	//总数
    	//db.user_addr.count()
    	template.count(query, Jet.class);
    	//枚举
    	//query.
    	//db.foo.distinct('msg')
    	template.getCollection("jet").distinct("speed");
    	
    	
    }
    
}
