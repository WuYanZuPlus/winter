package com.jianghu.winter.query.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/27 16:26
 */
@Getter
@AllArgsConstructor
public class PageList<T> {
    public final List<T> list;
    public final long total;
}
