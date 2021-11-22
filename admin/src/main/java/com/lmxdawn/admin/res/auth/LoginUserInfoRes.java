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
public class LoginUserInfoRes {
    @ApiModelProperty(value = "用户ID")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "头像")
    private String avatar;
    // 权限列表
    @ApiModelProperty(value = "权限列表")
    private List<String> authRules;
}
