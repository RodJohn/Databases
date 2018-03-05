package com.john.rod.springdatajdbc;

import org.h2.tools.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class MainApp {


    public static void main(String[] args) throws SQLException {


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        PersonRepository personRepository = (PersonRepository) ctx.getBean("personRepository");

    }


}
