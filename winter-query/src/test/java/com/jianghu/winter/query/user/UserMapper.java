package com.jianghu.winter.query.user;

import com.jianghu.winter.query.core.QueryMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:19
 */
@Mapper
public interface UserMapper extends QueryMapper<UserEntity,UserQuery> {

}
