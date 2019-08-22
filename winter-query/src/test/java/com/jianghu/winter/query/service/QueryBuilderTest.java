package com.jianghu.winter.query.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:11
 */
public class QueryBuilderTest {

    @Test
    public void buildSelect() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery build = UserQuery.builder().build();
        assertEquals("SELECT * FROM t_user", queryBuilder.buildSelect(build));
    }

    @Test
    public void buildSelectWithWhere() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery byAccount = UserQuery.builder().account("daniel").build();
        String actualSql = queryBuilder.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account}", actualSql);
    }

    @Test
    public void buildSelectWithWheres() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery byAccount = UserQuery.builder().account("daniel").userName("赵子龙").build();
        String actualSql = queryBuilder.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName}", actualSql);
    }
}