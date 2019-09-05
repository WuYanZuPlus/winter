package com.jianghu.winter.query.core;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * general interface
 * All interfaces using @Mapper annotations must extends this
 * 泛型注释:
 * E - entity (like UserEntity)
 * I - id (Long or Integer)
 * Q - query (like UserQuery)
 *
 * @author daniel.hu
 * @date 2019/8/27 10:26
 */
public interface QueryMapper<E, I, Q> {

    @SelectProvider(type = QueryBuilder.class, method = "buildSelect")
    List<E> query(Q query);

    @SelectProvider(type = QueryBuilder.class, method = "buildCount")
    long count(Q query);

    @Lang(MapperTableDriver.class)
    @Select("SELECT * FROM @{table} WHERE id = #{id}")
    E get(@Param("id") I id);

    @Lang(MapperTableDriver.class)
    @Delete("DELETE FROM @{table} WHERE id = #{id}")
    void delete(@Param("id") I id);

    /**
     * delete by query condition
     *
     * @param query query
     * @return delete count
     */
    @DeleteProvider(type = QueryBuilder.class, method = "buildDelete")
    int deleteByQuery(Q query);

    @InsertProvider(type = CrudBuilder.class, method = "buildInsert")
    void insert(E e);

    /**
     * batch insert
     *
     * @param entities entity 集合
     * @return batch insert count
     */
    @InsertProvider(type = CrudBuilder.class, method = "buildBatchInsert")
    int batchInsert(@Param("list") Iterable<E> entities);

    /**
     * update all fields, null value will be considered.
     *
     * @param e entity
     */
    @UpdateProvider(type = CrudBuilder.class, method = "buildUpdate")
    void update(E e);

    /**
     * Update fields that are not null
     *
     * @param e entity
     */
    @UpdateProvider(type = CrudBuilder.class, method = "buildPatch")
    void patch(E e);

    /**
     * Update fields for query condition
     *
     * @param e     entity
     * @param query query
     * @return update count
     */
    @UpdateProvider(type = CrudBuilder.class, method = "buildPatchByQuery")
    int patchByQuery(E e, Q query);
}
