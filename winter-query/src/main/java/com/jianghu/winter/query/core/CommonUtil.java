package com.jianghu.winter.query.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RegExUtils;

/**
 * @author daniel.hu
 * @date 2019/8/22 15:07
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    /**
     * 驼峰字段加下划线
     */
    public static String camelCaseToUnderscore(String value) {
        return RegExUtils.replaceAll(value, "([A-Z])", "_$1").toLowerCase();
    }

}
