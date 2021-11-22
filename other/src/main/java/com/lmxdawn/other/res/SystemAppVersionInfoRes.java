package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class SystemAppVersionInfoRes {

    @ApiModelProperty(value = "是否更新（0：否，1：是）")
    private Integer isUpdate = 0;

    @ApiModelProperty(value = "是否需要清除本地缓存（0：否，1：是）")
    private Integer isClearCache = 0;

    @ApiModelProperty(value = "是否强制更新（0：否，1：是）")
    private Integer isForceUpdate = 0;

    @ApiModelProperty(value = "类型[1全量更新 2增量更新]")
    private Integer type = 1;

    @ApiModelProperty(value = "更新日志")
    private String log = "";

    @ApiModelProperty(value = "更新链接")
    private String url = "";

    @ApiModelProperty(value = "最新版本")
    private String version = "";

    @ApiModelProperty(value = "创建时间")
    private Date createTime = new Date();
}
