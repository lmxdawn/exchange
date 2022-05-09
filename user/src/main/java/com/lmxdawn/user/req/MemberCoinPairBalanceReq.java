package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class MemberCoinPairBalanceReq {

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种ID不能为空")
    @Min(value = 1, message = "交易币种ID不能为空")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种ID不能为空")
    @Min(value = 1, message = "计价币种ID不能为空")
    private Long coinId;

}
