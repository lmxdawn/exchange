package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class LoginPwdReq {

    // 用户手机
    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "Phone number cannot be empty")
    private String tel;

    // 密码
    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "password can not be blank")
    private String pwd;

}
