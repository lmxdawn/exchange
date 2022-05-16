package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class SetPayPwdReq {

    @ApiModelProperty(value = "手机号或者邮箱号，具体看前端发送用的哪一个")
    @NotBlank(message = "手机号或者邮箱号不能为空")
    private String telOrEmail;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "password can not be blank")
    private String password;

}
