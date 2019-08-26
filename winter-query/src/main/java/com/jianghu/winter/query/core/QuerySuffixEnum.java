package com.jianghu.winter.query.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author daniel.hu
 * @date 2019/8/26 11:31
 */
@SuppressWarnings("squid:S00115")
@Getter
@AllArgsConstructor
public enum QuerySuffixEnum {
    Like(" LIKE "),
    In(" IN "),
    Default("=");
    private String value;
    private static final Pattern PATTERN_SUFFIX;

    static {
        List<String> suffixList = Arrays.stream(values()).filter(conditionEnum -> conditionEnum != Default)
                .map(Enum::name).collect(Collectors.toList());
        PATTERN_SUFFIX = Pattern.compile("(" + StringUtils.join(suffixList, "|") + ")$");
    }

    static QuerySuffixEnum resolve(String fieldName) {
        Matcher matcher = PATTERN_SUFFIX.matcher(fieldName);
        return matcher.find() ? valueOf(matcher.group()) : Default;
    }

    public String resolveColumnName(String fieldName) {
        String suffix = this.name();
        return fieldName.endsWith(suffix) ? StringUtils.substringBeforeLast(fieldName, suffix) : fieldName;
    }
}
