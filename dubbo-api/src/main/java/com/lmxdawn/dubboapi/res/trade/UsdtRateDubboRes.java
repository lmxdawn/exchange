package com.lmxdawn.dubboapi.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class UsdtRateDubboRes implements Serializable {

    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "符号")
    private String symbol;

    @ApiModelProperty(value = "USDT汇率")
    private Double price;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
