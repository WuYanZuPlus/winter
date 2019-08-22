package com.jianghu.winter.query.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}