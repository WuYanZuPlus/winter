package com.jianghu.winter.query.core;

import com.jianghu.winter.query.annotation.QueryTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

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
        String selectSql = "SELECT * FROM " + queryTable.table();
        List<Object> whereList = new LinkedList<>();
        for (Field field : query.getClass().getDeclaredFields()) {
            Object value = readFieldValue(query, field);
            if (value == null) {
                continue;
            }
            String columnName = CommonUtil.camelCaseToUnderscore(field.getName());
            whereList.add(columnName + " = " + "#{" + field.getName() + "}");
        }
        if (!whereList.isEmpty()) {
            String whereSql = " WHERE " + StringUtils.join(whereList, " AND ");
            selectSql += whereSql;
        }
        if (query instanceof PageQuery) {
            PageQuery pageQuery = (PageQuery) query;
            if (pageQuery.needPaging()) {
                String pageSql = " LIMIT " + pageQuery.getOffset() + "," + pageQuery.getPageSize();
                selectSql += pageSql;
            }
        }
        return selectSql;
    }

    private Object readFieldValue(Object query, Field field) {
        try {
            return FieldUtils.readField(field, query, true);
        } catch (IllegalAccessException e) {
            log.error("Get the field value exception by reflection: {}", e.getMessage());
        }
        return null;
    }
}
