package com.jianghu.winter.remote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author daniel.hu
 * @date 2019/6/21 14:37
 */
@Getter
@Setter
public class LanguageRequest {

    @ApiModelProperty(value = "租户号")
    @NotNull( message = "租户编号不能为空")
    private String tenantCode;

    @ApiModelProperty("店铺id")
    private String storeCode;
}
