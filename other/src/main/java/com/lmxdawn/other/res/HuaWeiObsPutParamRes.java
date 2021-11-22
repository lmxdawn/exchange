package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class HuaWeiObsPutParamRes {

    @ApiModelProperty(value = "签名URL")
    private String signedUrl;
}
