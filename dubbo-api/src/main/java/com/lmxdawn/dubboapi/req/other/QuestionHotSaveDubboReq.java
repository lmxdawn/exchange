package com.lmxdawn.dubboapi.req.other;

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
 * 热门问题添加/修改
 */
@ApiModel
@Data
public class QuestionHotSaveDubboReq implements Serializable {
    @ApiModelProperty(value = "ID，当修改时必传")
    private Long id;

    @ApiModelProperty(value = "标题（1-50个字符）")
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 50, message = "标题格式错误")
    private String title;

    @ApiModelProperty(value = "内容（1-1000个字符）")
    @NotBlank(message = "内容必传")
    @Length(min = 1, max = 1000, message = "内容格式错误")
    private String content;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：禁用，1：正常）")
    @NotNull(message = "状态必传")
    @Min(value = 0, message = "状态（0,1）")
    @Max(value = 1, message = "状态（0,1）")
    private Integer status;

}
