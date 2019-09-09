package com.jianghu.winter.query.user;

import com.jianghu.winter.query.annotation.QueryField;
import com.jianghu.winter.query.annotation.QueryTable;
import com.jianghu.winter.query.core.PageQuery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:12
 */
@Getter
@Setter
@Builder
@QueryTable(table = "t_user")
public class UserQuery extends PageQuery {
    private String account;
    private String userName;
    private String userNameLike;
    private Collection<Integer> idIn;
    private UserType userType;
    @QueryField(and = "(user_name = #{userNameOrNickName} OR nick_name = #{userNameOrNickName})")
    private String userNameOrNickName;
}
