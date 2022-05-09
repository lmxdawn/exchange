package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 查询交易对
@ApiModel
@Data
public class PairReadReq {

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种ID不能为空")
    @Min(value = 1, message = "交易币种ID不能为空")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种ID不能为空")
    @Min(value = 1, message = "计价币种ID不能为空")
    private Long coinId;

}
