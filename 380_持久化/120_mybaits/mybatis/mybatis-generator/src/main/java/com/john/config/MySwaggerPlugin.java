package com.john.config;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MySwaggerPlugin  extends PluginAdapter {

	private final String importStr = "io.swagger.annotations.ApiModelProperty;";

    public MySwaggerPlugin() {
        super();
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }
    

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
    	topLevelClass.addImportedType(importStr);
        return true;
    }

    
    public boolean modelFieldGenerated(Field field,
            TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable,
            Plugin.ModelClassType modelClassType) {
    	
		StringBuilder sb = new StringBuilder();
		sb.append("@ApiModelProperty(\"");
		sb.append(introspectedColumn.getRemarks());
		sb.append("\")");
		field.addJavaDocLine(sb.toString());

    	
        return true;
    }

}
