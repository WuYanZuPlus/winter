package com.jianghu.winter.query.core;

import com.jianghu.winter.query.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/30 18:02
 */
@Slf4j
public class CrudBuilderTest {
    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("test");
        userEntity.setEmail("123456@qq.com");
        userEntity.setMobile("18312341234");
        userEntity.setNickName("James");
        userEntity.setPassword("123456");
        userEntity.setValid(true);
        CrudBuilder crudBuilder = new CrudBuilder();
        String insertSql = crudBuilder.buildInsert(userEntity);
        log.info("insert statement:\n{}", insertSql);
        assertEquals("INSERT INTO t_user (account, user_name, password, mobile, email, nick_name, valid) VALUES (#{account}, #{userName}, #{password}, #{mobile}, #{email}, #{nickName}, #{valid})", insertSql);
    }

    @Test
    public void batchInsert() {
        List<UserEntity> list = new ArrayList<>();

        UserEntity user1 = new UserEntity();
        user1.setAccount("test1");
        user1.setEmail("123456@qq.com");
        user1.setMobile("18312341234");
        user1.setNickName("James");
        user1.setPassword("123456");
        user1.setValid(true);
        list.add(user1);

        UserEntity user2 = new UserEntity();
        user2.setAccount("test2");
        user2.setEmail("123456@qq.com");
        user2.setMobile("18312341234");
        user2.setNickName("James");
        user2.setPassword("123456");
        user2.setValid(true);
        list.add(user2);

        Map<Object, Object> map = new HashMap<>();
        map.put("list", list);

        CrudBuilder crudBuilder = new CrudBuilder();
        String batchInsertSql = crudBuilder.buildBatchInsert(map);
        log.info("insert statement:\n{}", batchInsertSql);
        assertEquals("INSERT INTO t_user (account, user_name, password, mobile, email, nick_name, valid) VALUES (#{list[0].account}, #{list[0].userName}, #{list[0].password}, #{list[0].mobile}, #{list[0].email}, #{list[0].nickName}, #{list[0].valid}), (#{list[1].account}, #{list[1].userName}, #{list[1].password}, #{list[1].mobile}, #{list[1].email}, #{list[1].nickName}, #{list[1].valid})", batchInsertSql);
    }


}