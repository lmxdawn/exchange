package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class VideoToImageReq {
    @ApiModelProperty(value = "视频key", required = true)
    @NotBlank(message = "视频key不能为空")
    private String key;
}
