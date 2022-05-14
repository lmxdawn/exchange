package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberLoginInfoRes {

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

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

}
