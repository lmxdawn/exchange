package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UploadUrlAndThumbDubboRes implements Serializable {

    @ApiModelProperty(value = "原url")
    private String url;

    @ApiModelProperty(value = "缩略图url")
    private String thumbUrl;

}
