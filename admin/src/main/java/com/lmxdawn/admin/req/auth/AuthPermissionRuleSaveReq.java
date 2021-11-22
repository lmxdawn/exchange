package com.lmxdawn.admin.req.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 权限规则的提交保存表单
 */
@ApiModel
@Data
public class AuthPermissionRuleSaveReq {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "父级ID")
    private Long pid;
    @NotEmpty(message = "请输入规则名称")
    @ApiModelProperty(value = "规则名称", required = true)
    private String name;
    @NotEmpty(message = "请输入规则标题")
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @NotNull(message = "请选择状态")
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
    @ApiModelProperty(value = "表达式")
    private String condition;
    @ApiModelProperty(value = "排序")
    private Integer listorder;

}
