/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.rod.jdbc.mongo.template;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.rod.jdbc.mongo.jet.Jet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Template_CUD {

	
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

    
    
    /*TODO 保存******************************************************************************/
    @Test public void save() {
    	//保存
    	//会保存pojo中不为null的值
    	//建议使用insert 是插入而不是覆盖  id存在 会报错
    	//插入完以后 会自动填充id
    	template.insert(su27);	
    	//批量保存
    	//template.insert(jets, Jet.class);

    	//保存和覆盖
    	//template.save();
    	System.out.println();
    }
    
    
    
    
    /*TODO 更新******************************************************************************/
    @Test public void update() {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("id").is("584a638b6252302f096eecc0"));
    	Update update =  new Update();
    	
    	//普通更新
    	update.set("speed", 3000);	//特别注意数据类型
    	update.set("level", 1);
    	update.set("color", color);
    	
    	//删除-特别注意
    	//update.set("color", null);// 保存的是null
    	//update.unset("color"); 	   //  会被删除 
    	
    	//增加--最好是类中存在
    	//update.set("append", "append");
    	
    	template.findAndModify(query, update, Jet.class);
    }
    
    
    /*TODO 删除******************************************************************************/
    @Test public void delete() {
    	template.dropCollection(Jet.class);
    	Query query = Query.query(Criteria.where("name").is("y20"));;
		template.remove(query, Jet.class);
    }
    
    
    
}
