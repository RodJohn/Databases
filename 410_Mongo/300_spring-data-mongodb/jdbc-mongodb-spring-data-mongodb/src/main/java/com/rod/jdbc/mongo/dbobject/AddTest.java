package com.rod.jdbc.mongo.dbobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.rod.jdbc.mongo.jet.Jet;

public class AddTest {
	
    @Autowired  MongoTemplate templateTest;
	
    
    private Jet j10, su27,y20;
    private ArrayList<Jet> jets = new ArrayList<Jet>();
    private ArrayList<String> color = new ArrayList<String>();

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
    @Test public void inset1() {
    	
    	templateTest.insert(j10);
    	
    }
    
    
}
