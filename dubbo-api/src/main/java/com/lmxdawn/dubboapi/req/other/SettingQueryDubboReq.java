package com.lmxdawn.dubboapi.req.other;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class SettingQueryDubboReq extends ListPageDubboReq {
    @ApiModelProperty(value = "模块（1：运营配置，2：文件存储相关，3：华为云短信）", position = 1)
    private Integer module;

    @ApiModelProperty(value = "配置的key", position = 2)
    private String key;

}
