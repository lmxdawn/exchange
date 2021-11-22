package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
@Data
public class SmsSendReq {
    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定，4：注销，5：找回密码）", required = true)
    @NotNull(message = "场景不能为空")
    private Integer scene;

    @ApiModelProperty(value = "区号")
    @NotBlank(message = "区号不能为空")
    private String countryCode;

    @ApiModelProperty(value = "手机号（11位）", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String tel;
}
