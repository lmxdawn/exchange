package com.lmxdawn.admin.res.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员页面的筛选的角色列表
 */
@ApiModel
@Data
public class AuthAdminRoleRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

}
