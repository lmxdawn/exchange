package com.lmxdawn.admin.req.auth;

import com.lmxdawn.admin.req.ListPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色的查询表单
 */
@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRoleQueryReq extends ListPageReq {
    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "状态（1：正常，0：禁用）")
    private Integer status;

}
