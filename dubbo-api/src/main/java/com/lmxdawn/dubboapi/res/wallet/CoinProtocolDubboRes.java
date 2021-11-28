package com.lmxdawn.dubboapi.res.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class CoinProtocolDubboRes implements Serializable {
    @ApiModelProperty(value = "协议ID自增")
    private Long id;

    @ApiModelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "rpc主机")
    private String rpcHost;

    @ApiModelProperty(value = "rpc登录用户")
    private String rpcUser;

    @ApiModelProperty(value = "rpc登录密码")
    private String rpcPwd;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（1：关闭，2：开启）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
