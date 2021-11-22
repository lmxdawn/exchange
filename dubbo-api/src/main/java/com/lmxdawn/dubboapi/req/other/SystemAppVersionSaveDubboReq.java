package com.lmxdawn.dubboapi.req.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * APP版本添加/修改
 */
@ApiModel
@Data
public class SystemAppVersionSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "更新类型（ 1：全量更新，2：增量更新）")
    @NotNull(message = "更新类型不能为空")
    @Min(value = 1, message = "更新类型值为1或2")
    @Max(value = 2, message = "更新类型值为1或2")
    private Integer type;

    @ApiModelProperty(value = "平台（1：苹果，2：安卓）")
    @NotNull(message = "平台不能为空")
    @Min(value = 1, message = "平台值为1或2")
    @Max(value = 2, message = "平台值为1或2")
    private Integer platform;

    @ApiModelProperty(value = "版本（1-20个字符）")
    @NotBlank(message = "版本不能为空")
    @Length(min = 1, max = 20, message = "版本格式错误")
    private String version;

    @ApiModelProperty(value = "下载链接（1-150个字符）")
    @NotBlank(message = "下载链接不能为空")
    @Length(min = 1, max = 150, message = "下载链接错误")
    private String url;

    @ApiModelProperty(value = "是否强制更新（ 0：否 ，1：是）")
    @NotNull(message = "是否强制更新不能为空")
    @Min(value = 0, message = "是否强制更新值为0或1")
    @Max(value = 1, message = "是否强制更新值为0或1")
    private Integer isForceUpdate;

    @ApiModelProperty(value = "是否清空缓存（ 0：否 ，1：是）")
    @NotNull(message = "是否清空缓存不能为空")
    @Min(value = 0, message = "是否清空缓存值为0或1")
    @Max(value = 1, message = "是否清空缓存值为0或1")
    private Integer isClearCache;

    @ApiModelProperty(value = "更新日志")
    private String log;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
