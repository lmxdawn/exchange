package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的授权提交表单
 */
@ApiModel
@Data
public class AuthRoleAuthReq {
    @NotNull(message = "请选择角色")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
    @NotEmpty(message = "请选择授权的权限规则")
    @ApiModelProperty(value = "权限规则", required = true)
    private List<Long> authRules;
}
