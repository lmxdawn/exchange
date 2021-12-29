package com.lmxdawn.market.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class DepthListRes {
    @ApiModelProperty(value = "买入深度")
    private List<DepthRes> buyList = new ArrayList<>();

    @ApiModelProperty(value = "卖出深度")
    private List<DepthRes> sellList = new ArrayList<>();
}
