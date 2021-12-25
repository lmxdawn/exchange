package com.lmxdawn.dubboapi.req.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * USDT汇率添加/修改
 */
@ApiModel
@Data
public class UsdtRateSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 10,message = "名称格式错误")
    private String name;

    @ApiModelProperty(value = "符号")
    @NotBlank(message = "符号不能为空")
    @Length(min = 1, max = 10,message = "符号格式错误")
    private String symbol;

    @ApiModelProperty(value = "价格")
    @Min(value = 0, message = "价格格式错误")
    private Double price;

    @ApiModelProperty(value = "精度")
    @Min(value = 0, message = "精度格式错误")
    private Integer precision;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
