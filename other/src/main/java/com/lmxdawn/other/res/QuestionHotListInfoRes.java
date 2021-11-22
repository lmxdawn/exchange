package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@ApiModel
@Data
public class QuestionHotListInfoRes {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
