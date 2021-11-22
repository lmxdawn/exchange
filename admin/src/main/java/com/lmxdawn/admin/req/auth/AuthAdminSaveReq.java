package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理员的提交保存表单
 */
@ApiModel
@Data
public class AuthAdminSaveReq {
    // id
    @ApiModelProperty(value = "用户ID")
    private Long id;
    // 昵称
    @NotEmpty(message = "请输入用户名")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    // 登录密码
    @ApiModelProperty(value = "密码")
    private String password;
    // 状态
    @NotNull(message = "请选择状态")
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
    // 角色ids
    private List<Long> roles;
}
