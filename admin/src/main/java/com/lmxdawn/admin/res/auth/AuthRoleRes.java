package com.lmxdawn.admin.res.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色视图
 */
@ApiModel
@Data
public class AuthRoleRes {
    @ApiModelProperty(value = "角色ID")
    private Long id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色父级ID")
    private Long pid;
    @ApiModelProperty(value = "状态（1：正常，0：禁用）")
    private Long status;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序，优先级，越小优先级越高")
    private Long listorder;

}
