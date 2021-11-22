package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class RegionListReq {
    @ApiModelProperty(value = "上级省份ID", required = true, example = "0")
    @NotNull(message = "上级省份不能为空")
    private Integer provinceId;

    @ApiModelProperty(value = "上级城市ID", required = true, example = "0")
    @NotNull(message = "上级城市不能为空")
    private Integer cityId;

    @ApiModelProperty(value = "上级县ID", required = true, example = "0")
    @NotNull(message = "上级区县不能为空")
    private Integer countyId;
}
