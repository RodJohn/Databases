package com.john.rod.springdata;

import org.h2.tools.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class MainApp {


    public static void main(String[] args) throws SQLException {

        Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", "8043"}).start();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        JdbcTemplate tpl = (JdbcTemplate) ctx.getBean("jdbcTemplate");
//        tpl.execute("CREATE TABLE TEST (NAME VARCHAR(20) )");
//        tpl.execute("INSERT INTO TEST VALUES ('lijun')");
        List<String> strings = tpl.queryForList("SELECT * FROM TEST ", String.class);
        strings.forEach(str-> System.out.println(str));
    }


}
