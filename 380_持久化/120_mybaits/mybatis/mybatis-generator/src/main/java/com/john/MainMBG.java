package com.john;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 1.生成
 * 		1.1生成实体类
 * 			类注释
 * 			基类
 * 			序列化字段
 * 			字段注释
 * 			无参/完整参构造
 * 			附加注解
 * 		1.2接口类
 * 			常用方法
 * 		1.3SQLMapper
 * 2.更新字段
 * 		2.1实体类能够同步修改
 *		
 */

public class MainMBG {
	

//	
//	@Test
//	public void test1() throws Exception{
//		   List<String> warnings = new ArrayList<String>();
//		   boolean overwrite = true;
//		   
//		   Configuration config = new Configuration();
////		   Context context = new Context(defaultModelType);
//		   config.addContext(context );
//		   
//		   
//		   context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");
//
//
//		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//		   myBatisGenerator.generate(null);
//        for (String string : warnings) {
//			System.out.println(string);
//		}
//	}
//	
	
	
	
	@Test
	public void test() throws Exception{
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		InputStream io = MainMBG.class.getResourceAsStream("/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(io);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		for (String string : warnings) {
			System.out.println(string);
		}
	}

}
