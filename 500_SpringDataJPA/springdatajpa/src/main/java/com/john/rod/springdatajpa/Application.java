package com.john.rod.springdatajpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application implements CommandLineRunner ,ApplicationContextAware{

    ApplicationContext applicationContext = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Override
    public void run(String... args) throws Exception {
//        CustomerRepository repository = applicationContext.getBean(CustomerRepository.class);
//        repository.findAll().forEach(one->{
//            System.out.println(one.toString());
//        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}