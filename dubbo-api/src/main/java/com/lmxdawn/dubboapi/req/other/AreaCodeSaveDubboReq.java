package com.lmxdawn.dubboapi.req.other;

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
 * 电话地区添加/修改
 */
@ApiModel
@Data
public class AreaCodeSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "语言（1-20个字符）")
    @NotBlank(message = "语言不能为空")
    @Length(min = 1, max = 50, message = "语言格式错误")
    private String lang;

    @ApiModelProperty(value = "地区名字（1-100个字符）")
    @NotBlank(message = "地区名字不能为空")
    @Length(min = 1, max = 100, message = "地区名字格式错误")
    private String name;

    @ApiModelProperty(value = "地区编号（1-1000个字符）")
    @NotNull(message = "地区编号必传")
    private Integer code;

    @ApiModelProperty(value = "排序（升序）")
    @NotNull(message = "排序必传")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
