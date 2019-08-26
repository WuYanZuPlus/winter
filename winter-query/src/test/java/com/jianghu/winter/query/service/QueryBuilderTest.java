package com.jianghu.winter.query.service;

import com.jianghu.winter.query.core.Processor;
import com.jianghu.winter.query.core.QueryBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:11
 */
public class QueryBuilderTest {

    private static final String ACCOUNT = "daniel";
    private QueryBuilder queryBuilder;

    @Before
    public void setUp() throws Exception {
        queryBuilder = new QueryBuilder();
        Map<String, Processor> suffixProcessorMap = new HashMap<>();
        suffixProcessorMap.put("defaultProcessor", new Processor.DefaultProcessor());
        suffixProcessorMap.put("inProcessor", new Processor.InProcessor());
        suffixProcessorMap.put("likeProcessor", new Processor.LikeProcessor());

        FieldUtils.writeField(queryBuilder, "suffixProcessorMap", suffixProcessorMap, true);
    }

    @Test
    public void buildSelect() {
        UserQuery build = UserQuery.builder().build();
        assertEquals("SELECT * FROM t_user", queryBuilder.buildSelect(build));
    }

    @Test
    public void Where() {
        UserQuery byAccount = UserQuery.builder().account(ACCOUNT).build();
        String actualSql = queryBuilder.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account}", actualSql);
    }

    @Test
    public void Wheres() {
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName}", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WheresAndPage() {
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        userQuery.setPageNumber(0);
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName} LIMIT 0,10", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void Sort() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setSort("user_name DESC");
        assertEquals("SELECT * FROM t_user ORDER BY user_name DESC", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WhereAndLike() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setUserNameLike("da");
        assertEquals("SELECT * FROM t_user WHERE user_name LIKE #{userNameLike}", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WhereAndIn() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setIdIn(Arrays.asList(1, 2, 3));
        assertEquals("SELECT * FROM t_user WHERE id IN (#{idIn[0]}, #{idIn[1]}, #{idIn[2]})", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WhereAndIn2() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setIdIn(new ArrayList<>());
        assertEquals("SELECT * FROM t_user", queryBuilder.buildSelect(userQuery));
    }

}