package com.jianghu.winter.query.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;

/**
 * @author daniel.hu
 * @date 2019/8/22 15:07
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    /**
     * 驼峰字段加下划线
     */
    public static String camelCaseToUnderscore(String value) {
        return RegExUtils.replaceAll(value, "([A-Z])", "_$1").toLowerCase();
    }

    public static <E> E first(Iterable<E> iterable) {
        Iterator<E> iterator = iterable.iterator();
        try {
            return iterator.hasNext() ? iterator.next() : null;
        } finally {
            if (iterator.hasNext()) {
                log.warn("Find more than one element of {}", iterator.next().getClass());
            }
        }
    }

    public static String reWriteLikeValue(String like) {
        if (StringUtils.isBlank(like)) {
            return like;
        }
        return "%" + RegExUtils.replaceAll(like, "[%|_]", "\\\\$0") + "%";
    }


}
