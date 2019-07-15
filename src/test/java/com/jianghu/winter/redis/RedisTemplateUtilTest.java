package com.jianghu.winter.redis;

import com.jianghu.winter.WinterApplication;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
    private RedisTemplateUtil<Object, Object> redisTemplateUtil;

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
    @Builder
    static class User implements Serializable {
        String name;
        int age;
    }

    @Test
    public void test_string_object() {

        redisTemplateUtil.set(USER1, User.builder().name("zhangsan").age(15).build());
        redisTemplateUtil.set(USER2, User.builder().name("lisi").age(20).build());

        assertTrue(redisTemplateUtil.exists(USER1));
        assertTrue(redisTemplateUtil.exists(USER2));

        User user = redisTemplateUtil.get(USER1, User.class);
        assertThat(user.name).isEqualTo("zhangsan");
        assertThat(user.age).isEqualTo(15);
    }
}