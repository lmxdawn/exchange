package com.lmxdawn.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class EchoReq {

    // 用户手机
    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "Phone number cannot be empty")
    private String tel;

}
