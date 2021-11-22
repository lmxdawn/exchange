package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel
@Data
public class SystemAppVersionInfoDubboRes implements Serializable {

    @ApiModelProperty(value = "Id")
    private Integer id;

    @ApiModelProperty(value = "平台（1：苹果，2：安卓）")
    private Integer platform;

    @ApiModelProperty(value = "是否更新（0：否，1：是）")
    private Integer isUpdate;

    @ApiModelProperty(value = "是否需要清除本地缓存（0：否，1：是）")
    private Integer isClearCache;

    @ApiModelProperty(value = "是否强制更新（0：否，1：是）")
    private Integer isForceUpdate;

    @ApiModelProperty(value = "类型[1全量更新 2增量更新]")
    private Integer type;

    @ApiModelProperty(value = "更新日志")
    private String log;

    @ApiModelProperty(value = "更新链接")
    private String url;

    @ApiModelProperty(value = "最新版本")
    private String version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;
}
