package com.john.rod.es;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp implements CommandLineRunner{

    public static void main(String[] args)  {
        SpringApplication.run(MainApp.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("yunxingle");
    }
}
