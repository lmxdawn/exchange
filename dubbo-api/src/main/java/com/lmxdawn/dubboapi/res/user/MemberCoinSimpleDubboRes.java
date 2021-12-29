package com.lmxdawn.dubboapi.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemberCoinSimpleDubboRes implements Serializable {

    @ApiModelProperty(value = "可用余额")
    private Double balance;

    @ApiModelProperty(value = "冻结余额")
    private Double frozenBalance;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;
}
