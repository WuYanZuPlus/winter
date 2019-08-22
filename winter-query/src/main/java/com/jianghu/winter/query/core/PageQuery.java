package com.jianghu.winter.query.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @author daniel.hu
 * @date 2019/8/22 15:32
 */
@Getter
@Setter
public class PageQuery {
    /**
     * 页数
     */
    private Integer pageNumber;
    /**
     * 每页大小
     */
    private Integer pageSize;

    public Integer getPageNumber() {
        return getOrDefault(pageNumber, 0, pageSize == null);
    }

    public Integer getPageSize() {
        return getOrDefault(pageSize, 10, pageNumber == null);
    }

    private Integer getOrDefault(Integer number, int defaultValue, boolean beNull) {
        if (number == null) {
            if (beNull) {
                return null;
            } else {
                number = defaultValue;
            }
        }
        return Math.max(0, number);
    }

    public Integer getOffset() {
        return needPaging() ? getPageNumber() * getPageSize() : 0;
    }

    public boolean needPaging() {
        return pageNumber != null || pageSize != null;
    }
}
