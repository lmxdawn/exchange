package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class SystemAppVersionReq {
    @ApiModelProperty(value = "平台（1：苹果，2：安卓）")
    @NotNull(message = "请选择平台")
    @Min(value = 1, message = "请选择平台")
    @Max(value = 2, message = "请选择平台")
    private Integer platform;

    @ApiModelProperty(value = "版本号")
    @NotBlank(message = "请上传版本号")
    private String version;
}
