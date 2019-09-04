package com.jianghu.winter.query.core;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:26
 */
public interface QueryMapper<E, I, Q> extends DataService<E, I, Q> {

    @SelectProvider(type = QueryBuilder.class, method = "buildSelect")
    List<E> query(Q query);

    @SelectProvider(type = QueryBuilder.class, method = "buildCount")
    long count(Q query);

    @Lang(MapperTableDriver.class)
    @Select("SELECT * FROM @{table} WHERE id = #{id}")
    E get(@Param("id") I id);

    @Lang(MapperTableDriver.class)
    @Select("DELETE FROM @{table} WHERE id = #{id}")
    void delete(@Param("id") I id);

    @SelectProvider(type = QueryBuilder.class, method = "buildDelete")
    void deleteByQuery(Q query);

    @InsertProvider(type = CrudBuilder.class, method = "buildInsert")
    void insert(E e);

    @InsertProvider(type = CrudBuilder.class, method = "buildBatchInsert")
    void batchInsert(@Param("list") Iterable<E> entities);

    /**
     * update all fields, null value will be considered.
     *
     * @param e entity
     */
    @InsertProvider(type = CrudBuilder.class, method = "buildUpdate")
    void update(E e);

    /**
     * Update fields that are not null
     *
     * @param e entity
     */
    @InsertProvider(type = CrudBuilder.class, method = "buildPatch")
    void patch(E e);
}
