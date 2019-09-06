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
public class QueryProvider {
    protected static final String LOG_SQL = "\nSQL: {}";
    private static final Map<String, Processor> suffixProcessorMap = new ConcurrentHashMap<>();

    static {
        suffixProcessorMap.put("defaultProcessor", new Processor.DefaultProcessor());
        suffixProcessorMap.put("inProcessor", new Processor.InProcessor());
        suffixProcessorMap.put("likeProcessor", new Processor.LikeProcessor());
    }

    public String buildSelect(Object query) {
        return build(query, Operation.SELECT);
    }

    public String buildCount(Object query) {
        return build(query, Operation.COUNT);
    }

    protected String build(Object query, Operation operation) {
        String selectSql = buildStartSql(query, operation);
        selectSql = buildWhereSql(selectSql, query);
        if (operation == Operation.SELECT && query instanceof PageQuery) {
            PageQuery pageQuery = (PageQuery) query;
            selectSql = buildSortSql(selectSql, pageQuery);
            selectSql = buildPageSql(selectSql, pageQuery);
        }
        log.debug(LOG_SQL, selectSql);
        return selectSql;
    }

    private String buildStartSql(Object query, Operation operation) {
        QueryTable queryTable = query.getClass().getAnnotation(QueryTable.class);
        if (queryTable == null) {
            throw new IllegalStateException("@QueryTable annotation unConfigured!");
        }
        String startSql = "";
        switch (operation) {
            case SELECT:
                startSql = "SELECT *";
                break;
            case COUNT:
                startSql = "SELECT COUNT(*)";
                break;
            case DELETE:
                startSql = "DELETE";
                break;
            default:
        }
        return startSql + " FROM " + queryTable.table();
    }

    protected String buildWhereSql(String selectSql, Object query) {
        List<String> whereList = new LinkedList<>();
        for (Field field : query.getClass().getDeclaredFields()) {
            Object fieldValue = readFieldValue(query, field);
            if (fieldValue == null) {
                continue;
            }
            String fieldName = field.getName();
            QuerySuffixEnum suffixEnum = QuerySuffixEnum.resolve(fieldName);
            String columnName = CommonUtil.camelCaseToUnderscore(suffixEnum.resolveColumnName(fieldName));
            suffixProcessorMap.get(suffixEnum.name().toLowerCase() + Processor.class.getSimpleName())
                    .process(whereList, columnName, fieldName, fieldValue);
            if (suffixEnum == QuerySuffixEnum.Like) {
                reWriteFieldValue(query, fieldName, CommonUtil.reWriteLikeValue(fieldValue.toString()));
            }
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

    protected Object readFieldValue(Object query, Field field) {
        try {
            return FieldUtils.readField(field, query, true);
        } catch (IllegalAccessException e) {
            log.error("Get the field value exception by reflection: {}", e.getMessage());
        }
        return null;
    }

    private void reWriteFieldValue(Object target, String fieldName, Object fieldValue) {
        try {
            FieldUtils.writeDeclaredField(target, fieldName, fieldValue, true);
        } catch (IllegalAccessException e) {
            log.error("Override exception for field value suffixed with like: {}", e.getMessage());
        }
    }
}
