package com.lmxdawn.market.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class KLineListReq {

    @ApiModelProperty(value = "交易币种ID")
    @NotNull(message = "交易币种ID不能为空")
    @Min(value = 1, message = "交易币种ID不能为空")
    private Long tradeCoinId;

    @ApiModelProperty(value = "计价币种ID")
    @NotNull(message = "计价币种ID不能为空")
    @Min(value = 1, message = "计价币种ID不能为空")
    private Long coinId;

    @ApiModelProperty(value = "时间类型（1min：1分钟,5min：5分钟,15min：15分钟,30min：30分钟,1hour：1小时,4hour：1小时,1day：1天,1week：1周,1month：1月）")
    @NotBlank(message = "时间类型不能为空")
    private String timeStr;

    @ApiModelProperty(value = "第一条时间戳")
    private Long time;

}
