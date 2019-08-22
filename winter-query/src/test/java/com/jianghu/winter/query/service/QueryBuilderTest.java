package com.jianghu.winter.query.service;

import com.jianghu.winter.query.core.QueryBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:11
 */
public class QueryBuilderTest {

    private static final String ACCOUNT = "daniel";

    @Test
    public void buildSelect() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery build = UserQuery.builder().build();
        assertEquals("SELECT * FROM t_user", queryBuilder.buildSelect(build));
    }

    @Test
    public void buildSelectWithWhere() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery byAccount = UserQuery.builder().account(ACCOUNT).build();
        String actualSql = queryBuilder.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account}", actualSql);
    }

    @Test
    public void buildSelectWithWheres() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName}", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void buildSelectWithWheresAndPage() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        userQuery.setPageNumber(0);
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName} LIMIT 0,10", queryBuilder.buildSelect(userQuery));
    }
}