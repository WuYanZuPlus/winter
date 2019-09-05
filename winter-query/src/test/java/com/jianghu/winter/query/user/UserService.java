package com.jianghu.winter.query.user;

import com.jianghu.winter.query.core.AbstractMybatisDataService;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @author daniel.hu
 * @date 2019/9/5 14:15
 */
@Service
public class UserService extends AbstractMybatisDataService<UserEntity, Integer, UserQuery> {

    @Getter
    private UserMapper mapper;

    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }
}
