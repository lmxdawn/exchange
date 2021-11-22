package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class HuaWeiObsPostParamRes {

    @ApiModelProperty(value = "上传URL")
    private String url;

    @ApiModelProperty(value = "对象名称")
    private String key;

    @ApiModelProperty(value = "x-obs-acl权限控制策略")
    private String xObsAcl;

    @ApiModelProperty(value = "Access Key")
    private String contentType;

    @ApiModelProperty(value = "Access Key")
    private String accessKeyId;

    @ApiModelProperty(value = "请求的安全策略描述")
    private String policy;

    @ApiModelProperty(value = "签名")
    private String signature;

}
