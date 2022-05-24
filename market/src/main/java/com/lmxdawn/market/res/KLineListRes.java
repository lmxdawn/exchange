package com.lmxdawn.market.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class KLineListRes {

    @ApiModelProperty(value = "时间")
    private Long time;

    @ApiModelProperty(value = "开盘")
    private BigDecimal open;

    @ApiModelProperty(value = "收盘")
    private BigDecimal close;

    @ApiModelProperty(value = "最低")
    private BigDecimal low;

    @ApiModelProperty(value = "最高")
    private BigDecimal high;

    @ApiModelProperty(value = "数量")
    private BigDecimal vol;
}
