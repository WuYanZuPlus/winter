package com.jianghu.winter.query.core;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author daniel.hu
 * @date 2019/9/4 16:09
 */
@Getter
@NoArgsConstructor
public enum Operation {
    SELECT, COUNT, DELETE, UPDATE, PATCH
}
