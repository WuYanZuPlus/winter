package com.jianghu.winter.query.core;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/26 15:43
 */
public interface Processor {
    void process(List<String> whereList, Object value, String fieldName);

    class DefaultProcessor implements Processor {
        @Override
        public void process(List<String> whereList, Object value, String fieldName) {
            whereList.add(CommonUtil.camelCaseToUnderscore(fieldName) + " = " + "#{" + fieldName + "}");
        }
    }

    class LikeProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.Like;

        @Override
        public void process(List<String> whereList, Object value, String fieldName) {
            String columnName = suffixEnum.resolveColumnName(fieldName);
            whereList.add(CommonUtil.camelCaseToUnderscore(columnName) + suffixEnum.getValue() + "#{" + fieldName + "}");
        }
    }

    class InProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.In;

        @Override
        public void process(List<String> whereList, Object value, String fieldName) {
            String columnName = suffixEnum.resolveColumnName(fieldName);
            List<Object> list = new LinkedList<>();
            if (value instanceof Collection) {
                Collection collection = (Collection) value;
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