package com.jianghu.winter.query.service;

import com.jianghu.winter.query.annotation.QueryTable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel.hu
 * @date 2019/8/22 10:47
 */
@Slf4j
public class QueryBuilder {
    public String buildSelect(Object query) {
        QueryTable queryTable = query.getClass().getAnnotation(QueryTable.class);
        if (queryTable == null) {
            throw new IllegalStateException("@QueryTable annotation unConfigured!");
        }
        return "SELECT * FROM " + queryTable.table();
    }
}
