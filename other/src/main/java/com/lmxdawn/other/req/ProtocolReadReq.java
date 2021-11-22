package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class ProtocolReadReq {

    @ApiModelProperty(value = "协议key（后台获取）")
    @NotBlank(message = "协议不能为空")
    private String key;

}
