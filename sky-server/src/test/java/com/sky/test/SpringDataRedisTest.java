package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testRedisOperations(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","luna");
        String name = (String)valueOperations.get("name");
        System.out.println(name);
    }
}
