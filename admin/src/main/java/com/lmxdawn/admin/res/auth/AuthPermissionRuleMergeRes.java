package com.lmxdawn.admin.res.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权限列表整合为多维数组的视图
 */
@ApiModel
@Data
public class AuthPermissionRuleMergeRes {

    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "上级ID")
    private Long pid;
    @ApiModelProperty(value = "权限名")
    private String name;
    @ApiModelProperty(value = "权限标题")
    private String title;
    @ApiModelProperty(value = "状态（1：正常，0：禁用）")
    private Long status;
    @ApiModelProperty(value = "规则表达式，为空表示存在就验证，不为空表示按照条件验证")
    private String condition;
    @ApiModelProperty(value = "排序，优先级，越小优先级越高")
    private Long listorder;

    // 一次性加载所有权限规则生成 tree 树形节点时需要
    private List<AuthPermissionRuleMergeRes> children;

}
