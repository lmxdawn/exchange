package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class EmailSendReq {
    @ApiModelProperty(value = "场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码）", required = true)
    @NotNull(message = "场景不能为空")
    private Integer scene;

    @ApiModelProperty(value = "语言（国际化）", required = true)
    @NotBlank(message = "语言不能为空")
    private String lang;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
