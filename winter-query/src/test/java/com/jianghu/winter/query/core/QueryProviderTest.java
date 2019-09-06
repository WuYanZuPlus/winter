package com.jianghu.winter.query.core;

import com.jianghu.winter.query.user.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:11
 */
public class QueryProviderTest {

    private static final String ACCOUNT = "daniel";
    private QueryProvider queryProvider;

    @Before
    public void setUp() {
        queryProvider = new QueryProvider();
    }

    @Test
    public void buildSelect() {
        UserQuery userQuery = UserQuery.builder().build();
        assertEquals("SELECT * FROM t_user", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void buildCount() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setPageNumber(0);
        userQuery.setPageSize(2);
        userQuery.setSort("account,asc");
        assertEquals("SELECT COUNT(*) FROM t_user", queryProvider.buildCount(userQuery));
    }

    @Test
    public void where() {
        UserQuery byAccount = UserQuery.builder().account(ACCOUNT).build();
        String actualSql = queryProvider.buildSelect(byAccount);
        assertEquals("SELECT * FROM t_user WHERE account = #{account}", actualSql);
    }

    @Test
    public void wheres() {
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName}", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void wheresAndPage() {
        UserQuery userQuery = UserQuery.builder().account(ACCOUNT).userName("赵子龙").build();
        userQuery.setPageNumber(0);
        assertEquals("SELECT * FROM t_user WHERE account = #{account} AND user_name = #{userName} LIMIT 0,10", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void sort() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setSort("user_name DESC");
        assertEquals("SELECT * FROM t_user ORDER BY user_name DESC", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void custom_sort() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setSort("FIELD(user_name,'" + StringUtils.join(Arrays.asList("daniel", "user1"), "','") + "')");
        assertEquals("SELECT * FROM t_user ORDER BY FIELD(user_name,'daniel','user1')", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void whereAndLike() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setUserNameLike("da");
        assertEquals("SELECT * FROM t_user WHERE user_name LIKE #{userNameLike}", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void whereAndIn() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setIdIn(Arrays.asList(1, 2, 3));
        assertEquals("SELECT * FROM t_user WHERE id IN (#{idIn[0]}, #{idIn[1]}, #{idIn[2]})", queryProvider.buildSelect(userQuery));
    }

    @Test
    public void whereAndIn2() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setIdIn(new ArrayList<>());
        assertEquals("SELECT * FROM t_user", queryProvider.buildSelect(userQuery));
    }



}