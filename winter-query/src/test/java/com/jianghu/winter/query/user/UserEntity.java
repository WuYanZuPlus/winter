package com.jianghu.winter.query.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:20
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class UserEntity {
    private Integer id;
    private String account;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private String mobile;
    private String email;
    @Column(name = "nick_name")
    private String nickName;
    private String valid;
}
