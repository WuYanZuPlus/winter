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

/**
 * @author daniel.hu
 * @date 2019/8/22 10:47
 */
@Slf4j
public class QueryBuilder {

    private static final Map<String, Processor> suffixProcessorMap = new ConcurrentHashMap<>();

    static {
        suffixProcessorMap.put("defaultProcessor", new Processor.DefaultProcessor());
        suffixProcessorMap.put("inProcessor", new Processor.InProcessor());
        suffixProcessorMap.put("likeProcessor", new Processor.LikeProcessor());
    }

    public String buildSelect(Object query) {
        return build(query, "SELECT *");
    }

    public String buildCount(Object query) {
        return build(query, "SELECT COUNT(*)");
    }

    public String buildDelete(Object query) {
        String deleteSql = buildStartSql(query, "DELETE");
        return buildWhereSql(deleteSql, query);
    }

    private String build(Object query, String operation) {
        String selectSql = buildStartSql(query, operation);
        selectSql = buildWhereSql(selectSql, query);
        if (StringUtils.equals(operation, "SELECT *") && query instanceof PageQuery) {
            PageQuery pageQuery = (PageQuery) query;
            selectSql = buildSortSql(selectSql, pageQuery);
            selectSql = buildPageSql(selectSql, pageQuery);
        }
        return selectSql;
    }

    private String buildStartSql(Object query, String operation) {
        QueryTable queryTable = query.getClass().getAnnotation(QueryTable.class);
        if (queryTable == null) {
            throw new IllegalStateException("@QueryTable annotation unConfigured!");
        }
        return operation + " FROM " + queryTable.table();
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
