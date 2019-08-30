package com.jianghu.winter.query.core;

/**
 * @author daniel.hu
 * @date 2019/8/30 18:10
 */
public interface IdSupport<I> {

    I getId();

    void setId(I id);
}
