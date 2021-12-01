package com.lmxdawn.dubboapi.req.wallet;

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
 * 币种添加/修改
 */
@ApiModel
@Data
public class CoinSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "币种别名（1-50个字符）")
    @NotBlank(message = "币种别名")
    @Length(min = 1, max = 50, message = "币种别名格式错误")
    private String name;

    @ApiModelProperty(value = "币种全称（1-50个字符）")
    @NotBlank(message = "币种全称")
    @Length(min = 1, max = 50, message = "币种全称格式错误")
    private String coinName;

    @ApiModelProperty(value = "币种单位（1-10个字符）")
    @NotBlank(message = "币种单位")
    @Length(min = 1, max = 10, message = "币种单位格式错误")
    private String symbol;

    @ApiModelProperty(value = "币种精度")
    @Min(value = 0, message = "币种精度格式错误")
    private Integer precision;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
