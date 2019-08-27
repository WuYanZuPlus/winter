package com.jianghu.winter.query.core;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:26
 */
public interface QueryMapper<E, Q> extends DataService<E, Q> {

    @SelectProvider(type = QueryBuilder.class, method = "buildSelect")
    List<E> query(Q query);

    @SelectProvider(type = QueryBuilder.class, method = "buildCount")
    long count(Q query);
}
