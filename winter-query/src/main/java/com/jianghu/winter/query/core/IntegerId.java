package com.jianghu.winter.query.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author daniel.hu
 * @date 2019/9/6 11:10
 */
@Getter
@Setter
@MappedSuperclass
public abstract class IntegerId implements Persistable<Integer>, Serializable {

    @Id
    @GeneratedValue
    protected Integer id;

}