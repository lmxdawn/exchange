package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改密码的表单
 */
@ApiModel
@Data
public class UpdatePasswordReq {

    @NotNull(message = "参数错误！")
    @ApiModelProperty(value = "管理员ID", required = true)
    private Long adminId;

    @NotEmpty(message = "请输入旧密码")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotEmpty(message = "请输入新密码")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
