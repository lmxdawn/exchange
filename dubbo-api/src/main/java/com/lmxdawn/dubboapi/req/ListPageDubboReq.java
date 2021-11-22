package com.lmxdawn.dubboapi.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@Data
public class ListPageDubboReq implements Serializable {

    @NotNull(message = "请选择第几页")
    @Min(message = "分页参数错误", value = 1)
    @ApiModelProperty(value = "页数（必须大于等于1的整数）", required = true, position = -2)
    private Integer page;

    @NotNull(message = "请填写每页查询数量")
    @Min(value = 1, message = "分页参数不能小于1")
    @Max(value = 50, message = "分页参数不能大于50")
    @ApiModelProperty(value = "每页返回多少（1-50）", required = true, position = -1)
    private Integer limit;
}