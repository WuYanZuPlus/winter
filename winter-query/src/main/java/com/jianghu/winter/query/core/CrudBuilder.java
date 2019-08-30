package com.jianghu.winter.query.core;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Table;

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

        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> columnNames = Arrays.stream(fields).map(this::resolveColumnName).collect(Collectors.toList());
        List<String> fieldValues = Arrays.stream(fields).map(field -> "#{" + field.getName() + "}").collect(Collectors.toCollection(ArrayList::new));
        return "INSERT INTO " + table.name() + "(" + StringUtils.join(columnNames, ", ") + ")" + " VALUES " + "(" + StringUtils.join(fieldValues, ", ") + ")";
    }

    private String resolveColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty() ? column.name() : CommonUtil.camelCaseToUnderscore(field.getName());
    }
}
