package com.lmxdawn.wsroute.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class LoginReq {

    @ApiModelProperty(value = "用户token，不传表示游客登录")
    private String token;

}
