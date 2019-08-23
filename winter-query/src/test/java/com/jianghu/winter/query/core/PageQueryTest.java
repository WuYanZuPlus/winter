package com.jianghu.winter.query.core;

import com.alibaba.fastjson.JSON;
import com.jianghu.winter.query.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author daniel.hu
 * @date 2019/8/23 11:20
 */
@Slf4j
public class PageQueryTest {

    @Test
    public void test_query_validate() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setSort("user_name,desc");
        List<FieldError> fieldErrors = BeanUtils.checkBean(pageQuery);
        log.info("校验结果:\n{}", JSON.toJSONString(fieldErrors, true));
        assertTrue(fieldErrors.isEmpty());
    }

    @Test
    public void test_sort_regex() {
        Pattern compile = Pattern.compile(PageQuery.REGEX_SORT);
        assertFalse(compile.matcher("user_name").matches());
        assertFalse(compile.matcher("user_name, asc").matches());
        assertTrue(compile.matcher("user_name,asc").matches());
        assertTrue(compile.matcher("user_name,desc").matches());
        assertTrue(compile.matcher("user_name,desc;mobile,asc").matches());
    }

    @Test
    public void needPaging() {
        PageQuery pageQuery = new PageQuery();
        assertFalse(pageQuery.needPaging());
        assertEquals(0, pageQuery.getOffset());
    }

    @Test
    public void needPagingAfterSetPageNumber() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(1);
        assertTrue(pageQuery.needPaging());
        assertEquals(1, (int) pageQuery.getPageNumber());
        assertEquals(10, (int) pageQuery.getPageSize());
    }

    @Test
    public void needPagingAfterSetPageSize() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageSize(10);
        assertTrue(pageQuery.needPaging());
        assertEquals(0, (int) pageQuery.getPageNumber());
        assertEquals(10, (int) pageQuery.getPageSize());
    }
}