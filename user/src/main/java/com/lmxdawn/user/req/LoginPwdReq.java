package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class LoginPwdReq {

    @ApiModelProperty(value = "类型（email：邮箱，tel：手机号）")
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "password can not be blank")
    private String password;

}
