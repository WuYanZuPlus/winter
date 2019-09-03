package com.jianghu.winter.query.core;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author daniel.hu
 * @date 2019/8/30 15:22
 */
public class CrudBuilder extends QueryBuilder {

    public String buildInsert(Object entity) {
        Table table = entity.getClass().getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalStateException("@Table annotation unConfigured!");
        }
        List<Field> allFields = FieldUtils.getAllFieldsList(entity.getClass());
        List<Field> filteredFields = allFields.stream().filter(field -> !isIgnoredField(field)).collect(Collectors.toList());

        List<String> columnNames = filteredFields.stream().map(this::resolveColumnName).collect(Collectors.toList());
        List<String> fieldValues = filteredFields.stream().map(field -> "#{" + field.getName() + "}").collect(Collectors.toList());
        return "INSERT INTO " + table.name() + "(" + StringUtils.join(columnNames, ", ") + ")" + " VALUES " + "(" + StringUtils.join(fieldValues, ", ") + ")";
    }

    private boolean isIgnoredField(Field field) {
        return Modifier.isStatic(field.getModifiers())
                || field.isAnnotationPresent(GeneratedValue.class)
                || field.isAnnotationPresent(Transient.class);
    }

    private String resolveColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty() ? column.name() : CommonUtil.camelCaseToUnderscore(field.getName());
    }
}
