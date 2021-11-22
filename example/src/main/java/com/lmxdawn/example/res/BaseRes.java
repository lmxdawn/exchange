package com.lmxdawn.example.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回结果类
 * @param <T>
 */
@ApiModel(value = "返回结果")
@Data
public class BaseRes<T> {

    @ApiModelProperty(value = "code码")
    private Integer code;

    @ApiModelProperty(value = "错误信息，成功则返回success")
    private String message;

    @ApiModelProperty(value = "成功返回的数据")
    private T data;
}
