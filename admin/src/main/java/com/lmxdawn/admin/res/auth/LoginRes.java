package com.lmxdawn.admin.res.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 登录用户的信息视图
 */
@ApiModel
@Data
public class LoginRes {
    @ApiModelProperty(value = "用户ID")
    private Long id;
    @ApiModelProperty(value = "登录的token")
    private String token;
}
