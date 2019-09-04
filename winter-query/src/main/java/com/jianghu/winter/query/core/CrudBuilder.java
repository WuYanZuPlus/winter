package com.jianghu.winter.query.core;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author daniel.hu
 * @date 2019/8/30 15:22
 */
@Slf4j
public class CrudBuilder extends QueryBuilder {
    private static final String LOG_SQL = "\nSQL: {}";
    private final Map<Class, String> insertSqlMap = new HashMap<>();

    /**
     * insert
     */
    public String buildInsert(Object entity) {
        String insertSql = insertSqlMap.computeIfAbsent(entity.getClass(), CrudBuilder::buildInsertSql);
        log.debug(LOG_SQL, insertSql);
        return insertSql;
    }

    /**
     * batch insert
     */
    public String buildBatchInsert(Map map) {
        List<Object> entities = (List<Object>) map.get("list");
        Class<?> clazz0 = entities.get(0).getClass();
        List<Field> allFields = FieldUtils.getAllFieldsList(clazz0);
        List<Field> filteredFields = allFields.stream().filter(field -> !isIgnoredField(field)).collect(Collectors.toList());
        List<String> columnNames = filteredFields.stream().map(CrudBuilder::resolveColumnName).collect(Collectors.toList());

        List<String> batchFieldValues = new ArrayList<>();
        List<String> fieldValues = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            for (Field field : filteredFields) {
                String format = String.format("#{list[%d].%s}", i, field.getName());
                fieldValues.add(format);
            }
            batchFieldValues.add("(" + StringUtils.join(fieldValues, ", ") + ")");
            fieldValues.clear();
        }
        return buildInsertSql(resolveTableName(clazz0), columnNames, StringUtils.join(batchFieldValues, ", "));
    }

    private static String buildInsertSql(Class<?> entityClass) {
        List<Field> allFields = FieldUtils.getAllFieldsList(entityClass);
        List<Field> filteredFields = allFields.stream().filter(field -> !isIgnoredField(field)).collect(Collectors.toList());
        List<String> columnNames = filteredFields.stream().map(CrudBuilder::resolveColumnName).collect(Collectors.toList());

        List<String> fieldValues = filteredFields.stream().map(field -> "#{" + field.getName() + "}").collect(Collectors.toList());
        return buildInsertSql(resolveTableName(entityClass), columnNames, "(" + StringUtils.join(fieldValues, ", ") + ")");
    }

    private static String buildInsertSql(String tableName, List<String> columnNames, String fieldValues) {
        ArrayList<String> insertList = new ArrayList<>();
        insertList.add("INSERT INTO");
        insertList.add(tableName);
        insertList.add("(" + StringUtils.join(columnNames, ", ") + ")");
        insertList.add("VALUES");
        insertList.add(fieldValues);
        return StringUtils.join(insertList, " ");
    }

    private static String resolveTableName(Class<?> clazz) {
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalStateException("@Table annotation unConfigured!");
        }
        return table.name();
    }

    private static boolean isIgnoredField(Field field) {
        return Modifier.isStatic(field.getModifiers())
                || field.isAnnotationPresent(GeneratedValue.class)
                || field.isAnnotationPresent(Transient.class);
    }

    private static String resolveColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty() ? column.name() : CommonUtil.camelCaseToUnderscore(field.getName());
    }

    /**
     * update
     */
    public String buildUpdate(Object entity) {
        String updateSql = buildUpdateSql(entity, Operation.UPDATE) + " WHERE id = #{id}";
        log.debug(LOG_SQL, updateSql);
        return updateSql;
    }

    /**
     * patch
     */
    public String buildPatch(Object entity) {
        String updateSql = buildUpdateSql(entity, Operation.PATCH) + " WHERE id = #{id}";
        log.debug(LOG_SQL, updateSql);
        return updateSql;
    }

    /**
     * patch by query
     */
    public String buildPatchByQuery(Object entity, Object query) {
        String updateSql = buildUpdateSql(entity, Operation.PATCH) + super.buildWhereSql("", query);
        log.debug(LOG_SQL, updateSql);
        return updateSql;
    }

    public String buildUpdateSql(Object entity, Operation operation) {
        ArrayList<String> updateList = new ArrayList<>();
        updateList.add("UPDATE");
        updateList.add(resolveTableName(entity.getClass()));
        updateList.add("SET");
        updateList.add(buildUpdateOrPatchFields(entity, operation));
        return StringUtils.join(updateList, " ");
    }

    private String buildUpdateOrPatchFields(Object entity, Operation operation) {
        List<Field> allFields = FieldUtils.getAllFieldsList(entity.getClass());
        List<Field> filteredFields = allFields.stream().filter(field -> !isIgnoredField(field)).collect(Collectors.toList());
        List<String> updateFields = new LinkedList<>();
        if (operation == Operation.UPDATE) {
            for (Field field : filteredFields) {
                updateFields.add(resolveColumnName(field) + " = " + "#{" + field.getName() + "}");
            }
        } else {
            for (Field field : filteredFields) {
                Object value = readFieldValue(entity, field);
                if (value != null) {
                    updateFields.add(resolveColumnName(field) + " = " + "#{" + field.getName() + "}");
                }
            }
        }
        return StringUtils.join(updateFields, ", ");
    }
}
