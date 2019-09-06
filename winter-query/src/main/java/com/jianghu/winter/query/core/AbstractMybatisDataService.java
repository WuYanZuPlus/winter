package com.jianghu.winter.query.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/9/5 13:52
 */
public abstract class AbstractMybatisDataService<E extends Persistable<I>, I extends Serializable, Q extends PageQuery> implements MybatisDataService<E, I, Q> {

    protected abstract IMapper<E, I, Q> getMapper();

    @Override
    public E get(I id) {
        return getMapper().get(id);
    }

    @Override
    public E get(Q query) {
        return CommonUtil.first(query(query));
    }

    @Override
    public void create(E entity) {
        getMapper().insert(entity);
    }

    @Override
    public int create(Iterable<E> entities) {
        return getMapper().batchInsert(entities);
    }

    @Override
    public void update(E entity) {
        getMapper().update(entity);
    }

    @Override
    public void patch(E entity) {
        getMapper().patch(entity);
    }

    @Override
    public int patch(E entity, Q query) {
        return getMapper().patchByQuery(entity, query);
    }

    @Override
    public void delete(I id) {
        getMapper().delete(id);
    }

    @Override
    public int delete(Q query) {
        return getMapper().deleteByQuery(query);
    }

    @Override
    public List<E> query(Q query) {
        return getMapper().query(query);
    }

    @Override
    public long count(Q query) {
        return getMapper().count(query);
    }
}
