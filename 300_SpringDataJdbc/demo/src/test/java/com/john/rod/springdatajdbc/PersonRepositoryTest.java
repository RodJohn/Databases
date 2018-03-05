package com.john.rod.springdatajdbc;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

import static org.junit.Assert.*;

public class PersonRepositoryTest {

    @Before
    public void setUp() throws Exception {
        Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", "8043"}).start();
    }

    @Test
    public void on() throws Exception {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        PersonRepository personRepository = (PersonRepository) ctx.getBean("personRepository");

        Person person = new Person();
        person.setId(111);
        personRepository.save(person);

        Optional<Person> one = personRepository.findById(111);
        System.out.println(one.get().getId());
    }





    @After
    public void tearDown() throws Exception {
    }
}