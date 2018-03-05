package com.john.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 生成缓存
 * 读取缓存--不会交叉
 * 
 * 跟新缓存
 * 缓存失效
 */

@CacheConfig(cacheNames = "cache_user")
public interface UserRepository extends JpaRepository<User, Long> {

	@Cacheable(key="#root.methodName+#p0")
    User findByName(String name);

	@Cacheable(key="#root.methodName+#p0")
	@Query(" from User u where u.name=:name")
	User findUserByName(@Param("name") String name);
	
	
    User findByNameAndAge(String name, Integer age);

/*    @CachePut
    //(key = "#p0.name")
    User save(User user);*/

}
