package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class ReviseLoginPwdReq {

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "password can not be blank")
    private String password;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "password can not be blank")
    private String newPassword;

}
