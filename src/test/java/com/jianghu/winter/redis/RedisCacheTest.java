package com.jianghu.winter.redis;

import com.alibaba.fastjson.JSONObject;
import com.jianghu.winter.WinterApplication;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author daniel.hu
 * @date 2019/8/7 19:48
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WinterApplication.class)
public class RedisCacheTest {

    private Cache cache;

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Before
    public void setUp() {
        cache = cacheManager.getCache(CacheConstant.TEST_TOKEN);
    }

    /**
     * 在序列化和反序列化时，Java实体类需要一个无参的构造方法
     */
    @Getter
    @Setter
    static class User {
        String name;
        int age;
    }

    @Test
    public void testCache() {
        cache.put("key1", "value");

        User user = new User();
        user.name = "daniel";
        user.age = 15;
        cache.put("key2", user);
        User userEntity = cache.get("key2", User.class);

        assertThat(userEntity)
                .hasFieldOrPropertyWithValue("name", "daniel")
                .hasFieldOrPropertyWithValue("age", 15);

    }

}

