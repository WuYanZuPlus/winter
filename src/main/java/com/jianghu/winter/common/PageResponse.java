package com.jianghu.winter.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * PageResponse
 *
 * @author daniel.hu
 * @date 2018-04-04
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResponse<T> {
    private Long total;
    private List<T> list;
}
