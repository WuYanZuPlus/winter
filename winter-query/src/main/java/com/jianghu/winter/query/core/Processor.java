package com.jianghu.winter.query.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
     * @param columnName columnName
     * @param fieldName  fieldName
     * @param fieldValue fieldValue
     */
    void process(List<String> whereList, String columnName, String fieldName, Object fieldValue);


    class DefaultProcessor implements Processor {
        @Override
        public void process(List<String> whereList, String columnName, String fieldName, Object fieldValue) {
            whereList.add(columnName + " = " + "#{" + fieldName + "}");
        }
    }

    @Slf4j
    class LikeProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.Like;

        @Override
        public void process(List<String> whereList, String columnName, String fieldName, Object fieldValue) {
            whereList.add(columnName + suffixEnum.getValue() + "#{" + fieldName + "}");
        }
    }

    class InProcessor implements Processor {
        private static final QuerySuffixEnum suffixEnum = QuerySuffixEnum.In;

        @Override
        public void process(List<String> whereList, String columnName, String fieldName, Object fieldValue) {
            List<Object> list = new LinkedList<>();
            if (fieldValue instanceof Collection) {
                Collection collection = (Collection) fieldValue;
                if (!collection.isEmpty()) {
                    for (int i = 0; i < collection.size(); i++) {
                        list.add("#{" + fieldName + "[" + i + "]" + "}");
                    }
                    whereList.add(columnName + suffixEnum.getValue() + "(" + StringUtils.join(list, ", ") + ")");
                }
            }
        }
    }

}