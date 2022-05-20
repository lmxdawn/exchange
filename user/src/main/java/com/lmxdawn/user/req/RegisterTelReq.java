package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@ApiModel
@Data
public class RegisterTelReq {

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "Phone cannot be empty")
    @Length(min = 11,max = 11,message = "The phone number can only be 11 digits")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "Malformed phone number")
    private String tel;

    @ApiModelProperty(value = "区号")
    @NotBlank(message = "区号不能为空")
    private String areaCode;

    @ApiModelProperty(value = "验证码（6位纯数字组成）", required = true)
    @NotBlank(message = "verification code must be filled")
    @Length(min = 6,max = 6,message = "Verification code format error")
    private String code;

    @ApiModelProperty(value = "密码（确认密码前端确认即可，密码为８-20位密码及数字组成）", required = true)
    @NotNull(message = "pwd can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "The password can only be 8 to 20 letters and numbers")
    private String password;
}
