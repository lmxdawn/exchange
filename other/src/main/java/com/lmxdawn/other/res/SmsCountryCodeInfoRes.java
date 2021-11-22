package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SmsCountryCodeInfoRes {

    @ApiModelProperty(value = "英文名称")
    private String englishName;

    @ApiModelProperty(value = "中文名称")
    private String chineseName;

    @ApiModelProperty(value = "国家区域Code")
    private String countryCode;

    @ApiModelProperty(value = "电话区号")
    private String phoneCode;

}
