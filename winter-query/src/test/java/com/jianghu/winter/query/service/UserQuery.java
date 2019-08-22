package com.jianghu.winter.query.service;

import com.jianghu.winter.query.annotation.QueryTable;
import lombok.Builder;

/**
 * @author daniel.hu
 * @date 2019/8/22 11:12
 */
@Builder
@QueryTable(table = "t_user")
public class UserQuery {
}
