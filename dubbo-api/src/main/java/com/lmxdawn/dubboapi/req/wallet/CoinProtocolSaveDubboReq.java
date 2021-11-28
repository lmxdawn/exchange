package com.lmxdawn.dubboapi.req.wallet;

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
 * 币种协议添加/修改
 */
@ApiModel
@Data
public class CoinProtocolSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "协议名称（1-10个字符）")
    @NotBlank(message = "协议名称")
    @Length(min = 1, max = 10, message = "协议名称格式错误")
    private String name;

    @ApiModelProperty(value = "rpc主机（1-50个字符）")
    @Length(min = 1, max = 50, message = "rpc主机格式错误")
    private String rpcHost;

    @ApiModelProperty(value = "rpc用户（1-50个字符）")
    @Length(min = 1, max = 50, message = "rpc用户格式错误")
    private String rpcUser;

    @ApiModelProperty(value = "rpc密码（1-50个字符）")
    @Length(min = 1, max = 50, message = "rpc密码格式错误")
    private String rpcPwd;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
