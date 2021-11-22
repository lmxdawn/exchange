package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录验证
 */
@ApiModel()
@Data
public class LoginReq {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String pwd;

}