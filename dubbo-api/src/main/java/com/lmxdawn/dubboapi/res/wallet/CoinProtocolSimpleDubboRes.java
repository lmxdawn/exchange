package com.lmxdawn.dubboapi.res.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class CoinProtocolSimpleDubboRes implements Serializable {
    @ApiModelProperty(value = "协议ID自增")
    private Long id;

    @ApiModelProperty(value = "协议名称")
    private String name;
}
