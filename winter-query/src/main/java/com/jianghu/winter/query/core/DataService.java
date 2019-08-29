package com.jianghu.winter.query.core;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/27 16:24
 */
public interface DataService<E, I, Q> {

    List<E> query(Q query);

    long count(Q query);

    default PageList<E> page(Q query) {
        return new PageList<>(query(query), count(query));
    }

    E get(@Param("id") I id);

    default E getByQuery(Q query) {
        return CommonUtil.first(query(query));
    }

    void delete(@Param("id") I id);

    void deleteByQuery(Q query);

}
