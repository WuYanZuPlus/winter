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
    public void Where() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery byAccount = UserQuery.builder().account(ACCOUNT).build();
        String actualSql = queryBuilder.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account}", actualSql);
    }

    @Test
    public void Wheres() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName}", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WheresAndPage() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        userQuery.setPageNumber(0);
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName} LIMIT 0,10", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void Sort() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setSort("user_name DESC");
        assertEquals("SELECT * FROM t_user ORDER BY user_name DESC", queryBuilder.buildSelect(userQuery));
    }

    @Test
    public void WhereAndLike() {
        QueryBuilder queryBuilder = new QueryBuilder();
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setUserNameLike("da");
        assertEquals("SELECT * FROM t_user WHERE user_name LIKE #{userNameLike}", queryBuilder.buildSelect(userQuery));
    }

}