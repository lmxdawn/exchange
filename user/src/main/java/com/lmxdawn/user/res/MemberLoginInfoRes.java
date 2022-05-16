package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberLoginInfoRes {

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "手机号区号")
    private String telAreaCode;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "个性签名")
    private String remark;

    @ApiModelProperty(value = "是否设置了登录密码")
    private Integer isPwd;

    @ApiModelProperty(value = "是否设置了支付密码")
    private Integer isPayPwd;

    @ApiModelProperty(value = "是否设置了谷歌验证码")
    private Integer isGoogleKey;

}
