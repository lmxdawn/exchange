package com.lmxdawn.dubboapi.res.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SettingInfoDubboRes implements Serializable {

    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "模块（1：运营配置，2：文件存储相关，3：华为云短信）")
    private Integer module;

    @ApiModelProperty(value = "配置的key")
    private String key;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "具体值")
    private String value;

    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;

}
