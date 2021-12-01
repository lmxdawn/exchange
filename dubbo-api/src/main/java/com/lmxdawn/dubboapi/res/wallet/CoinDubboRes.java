package com.lmxdawn.dubboapi.res.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class CoinDubboRes implements Serializable {
    @ApiModelProperty(value = "币种ID自增")
    private Long id;

    @ApiModelProperty(value = "币种别名")
    private String name;

    @ApiModelProperty(value = "币种全称")
    private String coinName;

    @ApiModelProperty(value = "币种单位")
    private String symbol;

    @ApiModelProperty(value = "币种精度")
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
