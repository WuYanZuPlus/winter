package com.jianghu.winter.query.core;

import com.jianghu.winter.query.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author daniel.hu
 * @date 2019/8/30 18:02
 */
@Slf4j
public class CrudBuilderTest {
    @Test
    public void name() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("test");
        userEntity.setEmail("123456@qq.com");
        userEntity.setMobile("18312341234");
        userEntity.setNickName("James");
        userEntity.setPassword("123456");
        userEntity.setValid(true);
        CrudBuilder crudBuilder = new CrudBuilder();
        log.info("insert statement:\n{}", crudBuilder.buildInsert(userEntity));
        assertEquals("INSERT INTO t_user(id, account, user_name, password, mobile, email, nick_name, valid) VALUES (#{id}, #{account}, #{userName}, #{password}, #{mobile}, #{email}, #{nickName}, #{valid})", crudBuilder.buildInsert(userEntity));
    }
}