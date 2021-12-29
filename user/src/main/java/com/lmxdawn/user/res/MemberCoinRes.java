package com.lmxdawn.user.res;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberCoinRes {

    @ApiModelProperty(value = "币种信息")
    private CoinSimpleDubboRes coin;

    @ApiModelProperty(value = "可用余额")
    private Double balance;

    @ApiModelProperty(value = "冻结余额")
    private Double frozenBalance;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

}
