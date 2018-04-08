package com.john.rod.springdata;


import org.h2.tools.Server;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.*;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Config implements ApplicationListener {


    @Bean
    public DataSource initDataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("org.h2.Driver");
        source.setUrl("jdbc:h2:./h2db/sxaz42b4");
        source.setUsername("sa");
        source.setPassword("sa");
        return source;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate JdbcTemplate(){
        return new JdbcTemplate(initDataSource());
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event");
    }
}
