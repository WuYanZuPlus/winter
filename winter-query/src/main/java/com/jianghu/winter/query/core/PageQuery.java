package com.jianghu.winter.query.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

/**
 * @author daniel.hu
 * @date 2019/8/22 15:32
 */
@Getter
@Setter
public class PageQuery {

    static final String REGEX_SORT = "(\\w+,(asc|desc);)*\\w+,(asc|desc)";

    private Integer pageNumber;

    /**
     * The size of each page
     */
    private Integer pageSize;

    /**
     * Notice: This field must be one of the table fields from the database
     */
    @ApiModelProperty(value = "Sorting field, format: field1,desc;field2,asc")
    @Pattern(regexp = REGEX_SORT, message = "Sorting field format error")
    private String sort;

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

    public int getOffset() {
        return needPaging() ? getPageNumber() * getPageSize() : 0;
    }

    public boolean needPaging() {
        return pageNumber != null || pageSize != null;
    }
}
