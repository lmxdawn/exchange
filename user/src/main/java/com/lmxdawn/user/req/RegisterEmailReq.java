package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
@Data
public class RegisterEmailReq {

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @ApiModelProperty(value = "验证码（6位纯数字组成）", required = true)
    @NotBlank(message = "verification code must be filled")
    @Length(min = 6,max = 6,message = "Verification code format error")
    private String code;

    @ApiModelProperty(value = "密码（确认密码前端确认即可，密码为８-20位密码及数字组成）", required = true)
    @NotNull(message = "pwd can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "The password can only be 8 to 20 letters and numbers")
    private String password;
}
