package com.jianghu.winter.query.user;

import com.jianghu.winter.query.core.IdSupport;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:20
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class UserEntity implements IdSupport<Integer> {
    @Id
    @GeneratedValue
    private Integer id;

    private String account;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private String mobile;
    private String email;
    @Column(name = "nick_name")
    private String nickName;
    private boolean valid;
    private UserType userType;
}
