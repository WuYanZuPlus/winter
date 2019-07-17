package com.jianghu.winter.redis;

import com.jianghu.winter.WinterApplication;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import javax.annotation.Resource;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author daniel.hu
 * @date 2019/7/15 15:49
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WinterApplication.class)
public class RedisTemplateUtilTest {
    private static final String KEY1 = "winter:test:key1";
    private static final String KEY2 = "winter:test:key2";
    private static final String USER1 = "winter:test:user1";
    private static final String USER2 = "winter:test:user2";

    @Resource
    private RedisTemplateUtil<String, Object> redisTemplateUtil;

    @Test
    public void test_string_string() {

        redisTemplateUtil.set(KEY1, "value1");
        redisTemplateUtil.set(KEY2, "value2", 20);

        assertTrue(redisTemplateUtil.exists(KEY1));
        assertTrue(redisTemplateUtil.exists(KEY2));

        assertEquals("value1", redisTemplateUtil.get(KEY1));
        assertEquals("value2", redisTemplateUtil.get(KEY2));
        assertTrue(redisTemplateUtil.getExpire(KEY2) > 10);

        redisTemplateUtil.expire(KEY2, 30);
        assertTrue(redisTemplateUtil.getExpire(KEY2) > 20);

        redisTemplateUtil.delete(KEY1);
        assertFalse(redisTemplateUtil.exists(KEY1));

    }

    @Getter
    @Setter
    static class User implements Serializable {
        String name;
        int age;
    }

    @Test
    public void test_string_object() {
        User user1 = new User();
        user1.name = "zhangsan";
        user1.age = 15;

        User user2 = new User();
        user2.name = "lisi";
        user2.age = 20;

        redisTemplateUtil.set(USER1, user1);
        redisTemplateUtil.set(USER2, user2);

        assertTrue(redisTemplateUtil.exists(USER1));
        assertTrue(redisTemplateUtil.exists(USER2));

        User user = redisTemplateUtil.get(USER1, User.class);
        assertThat(user.name).isEqualTo("zhangsan");
        assertThat(user.age).isEqualTo(15);
    }
}