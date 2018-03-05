package com.john.config;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class MyMethodPlugin  extends PluginAdapter {


    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            columns = introspectedTable.getNonBLOBColumns();
        } else {
            columns = introspectedTable.getAllColumns();
        }

        generateConstruction(topLevelClass, columns, introspectedTable);

        return true;
    }



    protected void generateConstruction(
    		TopLevelClass topLevelClass,
            List<IntrospectedColumn> introspectedColumns,
            IntrospectedTable introspectedTable) {
    	
        Method method = new Method();
        method.setConstructor(true);
        method.setVisibility(JavaVisibility.PUBLIC);
        String name = JavaBeansUtil.getCamelCaseString(introspectedTable.getFullyQualifiedTable().toString(),true);
        method.setName(name); 
        method.addBodyLine("");

        topLevelClass.addMethod(method);
    }
    
    
    

}