package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页的表单
 */
@ApiModel
@Data
public class ListPageReq {

    @NotNull(message = "请选择第几页")
    @Min(message = "分页参数错误", value = 1)
    @ApiModelProperty(value = "页数", required = true)
    private Integer page;

    @ApiModelProperty(value = "分页下标", hidden = true)
    private Integer offset;

    @NotNull(message = "请填写每页查询数量")
    @Min(value = 1, message = "分页参数不能小于1")
    @Max(value = 5000, message = "分页参数不能大于5000")
    @ApiModelProperty(value = "每页返回多少", required = true)
    private Integer limit;

}
