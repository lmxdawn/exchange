package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class HuaWeiObsReq {
    @ApiModelProperty(value = "文件后缀", required = true)
    @NotBlank(message = "文件后缀不能为空")
    private String suffix;
}
