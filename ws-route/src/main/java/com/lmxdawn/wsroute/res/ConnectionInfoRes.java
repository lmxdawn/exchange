package com.lmxdawn.wsroute.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 连接信息
 */
@ApiModel(value = "连接信息")
@Data
public class ConnectionInfoRes {

    @ApiModelProperty(value = "用户ID")
    private String memberId;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "http端口")
    private String httpPort;

    @ApiModelProperty(value = "ws端口")
    private String wsPort;
}
