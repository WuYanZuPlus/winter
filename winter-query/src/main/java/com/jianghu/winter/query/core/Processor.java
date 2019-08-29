package com.jianghu.winter.query.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/26 15:43
 */
public interface Processor {
    /**
     * @param whereList  where sql
     * @param target     target query object
     * @param fieldName  fieldName
     * @param fieldValue fieldValue
     */
    void process(List<String> whereList, Object target, String fieldName, Object fieldValue);

    class DefaultProcessor implements Processor {
        @Override
        public void process(List<String> whereList, Object target, String fieldName, Object fieldValue) {
            whereList.add(CommonUtil.camelCaseToUnderscore(fieldName) + " = " + "#{" + fieldName + "}");
        }
    }

    @Slf4j
    class LikeProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.Like;

        @Override
        public void process(List<String> whereList, Object target, String fieldName, Object fieldValue) {
            String columnName = suffixEnum.resolveColumnName(fieldName);
            whereList.add(CommonUtil.camelCaseToUnderscore(columnName) + suffixEnum.getValue() + "#{" + fieldName + "}");
            reWriteFieldValue(target, fieldName, fieldValue);
        }

        private void reWriteFieldValue(Object target, String fieldName, Object fieldValue) {
            try {
                FieldUtils.writeDeclaredField(target, fieldName, CommonUtil.reWriteLikeValue(fieldValue.toString()), true);
            } catch (IllegalAccessException e) {
                log.error("Override exception for field value suffixed with like: {}", e.getMessage());
            }
        }
    }

    class InProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.In;

        @Override
        public void process(List<String> whereList, Object target, String fieldName, Object fieldValue) {
            String columnName = suffixEnum.resolveColumnName(fieldName);
            List<Object> list = new LinkedList<>();
            if (fieldValue instanceof Collection) {
                Collection collection = (Collection) fieldValue;
                if (!collection.isEmpty()) {
                    for (int i = 0; i < collection.size(); i++) {
                        list.add("#{" + fieldName + "[" + i + "]" + "}");
                    }
                    whereList.add(CommonUtil.camelCaseToUnderscore(columnName) + suffixEnum.getValue() + "(" + StringUtils.join(list, ", ") + ")");
                }
            }
        }
    }

}