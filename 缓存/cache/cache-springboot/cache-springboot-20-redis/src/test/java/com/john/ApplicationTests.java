package com.john;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.john.domain.User;
import com.john.domain.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CacheManager cacheManager;

    @Before
    public void before() {
        userRepository.save(new User("AAA", 10));
        userRepository.save(new User("BBB", 10));
    }

    @Test
    public void test() throws Exception {
        User u10 = userRepository.findByName("AAA");
        System.out.println("findByName第一次查询：" + u10);

        User u11 = userRepository.findByName("AAA");
        System.out.println("findByName第二次查询相同条件：" + u11);
        
        User u12 = userRepository.findByName("BBB");
        System.out.println("findByName第二次查询不同条件：" + u12);
        
        User u20 = userRepository.findUserByName("AAA");
        System.out.println("findUserByName第一次查询：" + u20);
        
        User u21 = userRepository.findUserByName("AAA");
        System.out.println("findUserByName第二次查询相同条件：" + u21);
        
        User u22 = userRepository.findUserByName("BBB");
        System.out.println("findUserByName第二次查询不同条件：" + u22);
    }
}
