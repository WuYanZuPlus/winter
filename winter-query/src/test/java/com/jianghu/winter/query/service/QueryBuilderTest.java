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
        assertEquals("SELECT * FROM t_user", queryBuilder.buildSelect(UserQuery.builder().build()));
    }
}