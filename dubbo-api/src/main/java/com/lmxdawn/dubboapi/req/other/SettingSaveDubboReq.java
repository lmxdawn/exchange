package com.lmxdawn.dubboapi.req.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 配置
 */
@ApiModel
@Data
public class SettingSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID", position = 1)
    @NotNull(message = "Id必传")
    private Integer id;

    @ApiModelProperty(value = "配置的值", position = 2)
    @NotNull(message = "value必传")
    private String value;

}
