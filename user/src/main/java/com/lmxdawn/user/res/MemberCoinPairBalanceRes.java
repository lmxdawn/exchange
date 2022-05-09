package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberCoinPairBalanceRes {

    @ApiModelProperty(value = "交易币种可用余额")
    private Double tradeBalance;

    @ApiModelProperty(value = "计价币种可用余额")
    private Double balance;

}
