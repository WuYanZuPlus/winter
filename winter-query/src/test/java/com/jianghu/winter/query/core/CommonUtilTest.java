package com.jianghu.winter.query.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * @author daniel.hu
 * @date 2019/8/22 15:11
 */
public class CommonUtilTest {

    @Test
    public void test_camelCaseToUnderscore() {
        assertEquals("account", CommonUtil.camelCaseToUnderscore("account"));
        assertEquals("user_name", CommonUtil.camelCaseToUnderscore("userName"));
        assertEquals("login_error_number", CommonUtil.camelCaseToUnderscore("loginErrorNumber"));
    }

    @Test
    public void first() {
        assertNull(CommonUtil.first(new LinkedList<>()));
        assertEquals("hello", CommonUtil.first(Arrays.asList("hello")));
        assertEquals("hello", CommonUtil.first(Arrays.asList("hello", "world")));
    }

    @Test
    public void escapeLike() {
        assertNull(CommonUtil.reWriteLikeValue(null));
        assertEquals("", CommonUtil.reWriteLikeValue(""));

        assertEquals("%daniel%", CommonUtil.reWriteLikeValue("daniel"));
        assertNotEquals("%%%", CommonUtil.reWriteLikeValue("%"));
        assertEquals("%\\%%", CommonUtil.reWriteLikeValue("%"));
        assertEquals("%daniel\\%%", CommonUtil.reWriteLikeValue("daniel%"));

        assertNotEquals("%_%", CommonUtil.reWriteLikeValue("_"));
        assertEquals("%\\_%", CommonUtil.reWriteLikeValue("_"));

    }
}