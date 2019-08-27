package com.jianghu.winter.query.core;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/27 16:24
 */
public interface DataService<E, Q> {

    List<E> query(Q query);

    long count(Q query);

    default PageList<E> page(Q query) {
        return new PageList<>(query(query), count(query));
    }

}
