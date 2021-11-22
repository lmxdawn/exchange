package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 角色的提交保存表单
 */
@ApiModel
@Data
public class AuthRoleSaveReq {
    @ApiModelProperty(value = "权限ID")
    private Long id;
    @NotEmpty(message = "请输入权限名")
    @ApiModelProperty(value = "权限名", required = true)
    private String name;
    @ApiModelProperty(value = "权限上级ID")
    private Long pid;
    @ApiModelProperty(value = "状态（1：正常，0：禁用）")
    private Long status;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Long listorder;
}
