package com.jianghu.winter.query.core;

import java.beans.Transient;
import java.io.Serializable;

/**
 * Simple interface for entities.
 *
 * @param <I> the type of the identifier
 * @author daniel.hu
 * @date 2019/9/6 11:10
 */
public interface Persistable<I> extends Serializable {
    /**
     * Returns the id of the entity.
     */
    I getId();

    void setId(I id);

    @Transient
    default boolean isNew() {
        return getId() == null;
    }
}
