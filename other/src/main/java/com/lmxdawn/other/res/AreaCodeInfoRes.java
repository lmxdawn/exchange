package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class AreaCodeInfoRes {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "地区名字")
    private String name;
    @ApiModelProperty(value = "地区编号")
    private Integer code;
}
