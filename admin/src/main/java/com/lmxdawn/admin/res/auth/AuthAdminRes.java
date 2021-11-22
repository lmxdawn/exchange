package com.lmxdawn.admin.res.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 后台管理的 管理员管理页面的 VO
 */
@ApiModel
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 如果加该注解的字段为null,那么就不序列化这个字段
public class AuthAdminRes {

    // 主键
    @ApiModelProperty(value = "管理员ID")
    private Long id;
    // 昵称
    @ApiModelProperty(value = "用户名")
    private String username;
    // 最后登录ip
    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;
    // 最后登录时间
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    // 状态
    @ApiModelProperty(value = "用户状态（0：禁用； 1：正常 ；2：未验证）")
    private Integer status;
    // 角色ids
    @ApiModelProperty(value = "角色列表")
    private List<Long> roles;

}
