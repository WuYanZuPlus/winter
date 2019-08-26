package com.jianghu.winter.query.core;

import com.jianghu.winter.query.annotation.QueryTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;

/**
 * @author daniel.hu
 * @date 2019/8/22 10:47
 */
@Slf4j
public class QueryBuilder {

    @Resource
    private Map<String, Processor> suffixProcessorMap = new ConcurrentHashMap<>();

    public String buildSelect(Object query) {
        String selectSql = buildStartSql(query);
        selectSql = buildWhereSql(selectSql, query);
        if (query instanceof PageQuery) {
            PageQuery pageQuery = (PageQuery) query;
            selectSql = buildSortSql(selectSql, pageQuery);
            selectSql = buildPageSql(selectSql, pageQuery);
        }
        return selectSql;
    }

    private String buildStartSql(Object query) {
        QueryTable queryTable = query.getClass().getAnnotation(QueryTable.class);
        if (queryTable == null) {
            throw new IllegalStateException("@QueryTable annotation unConfigured!");
        }
        return "SELECT * FROM " + queryTable.table();
    }

    private String buildWhereSql(String selectSql, Object query) {
        List<String> whereList = new LinkedList<>();
        for (Field field : query.getClass().getDeclaredFields()) {
            Object value = readFieldValue(query, field);
            if (value == null) {
                continue;
            }
            String fieldName = field.getName();
            QuerySuffixEnum conditionEnum = QuerySuffixEnum.resolve(fieldName);
            String key = conditionEnum.name().toLowerCase() + Processor.class.getSimpleName();
            suffixProcessorMap.get(key).process(whereList, value, fieldName);
        }
        if (!whereList.isEmpty()) {
            String whereSql = " WHERE " + StringUtils.join(whereList, " AND ");
            selectSql += whereSql;
        }
        return selectSql;
    }

    private String buildSortSql(String selectSql, PageQuery pageQuery) {
        if (StringUtils.isNotBlank(pageQuery.getSort())) {
            selectSql += " ORDER BY " + pageQuery.getSort();
        }
        return selectSql;
    }

    private String buildPageSql(String selectSql, PageQuery pageQuery) {
        if (pageQuery.needPaging()) {
            String pageSql = " LIMIT " + pageQuery.getOffset() + "," + pageQuery.getPageSize();
            selectSql += pageSql;
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
