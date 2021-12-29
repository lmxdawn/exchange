package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 查询交易对
@ApiModel
@Data
public class SymbolReadReq {

    @ApiModelProperty(value = "交易币种ID")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    private Long coinId;

}
