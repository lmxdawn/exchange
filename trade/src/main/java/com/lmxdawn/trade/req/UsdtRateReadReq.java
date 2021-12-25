package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

// 查询交易对列表
@ApiModel
@Data
public class UsdtRateReadReq{

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

}
