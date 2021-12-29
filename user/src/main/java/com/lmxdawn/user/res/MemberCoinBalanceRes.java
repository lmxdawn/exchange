package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberCoinBalanceRes {

    @ApiModelProperty(value = "可用余额")
    private Double balance;

}
