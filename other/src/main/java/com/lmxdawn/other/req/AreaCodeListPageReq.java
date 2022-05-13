package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class AreaCodeListPageReq extends ListPageReq{

    @NotBlank(message = "请选择语言")
    @ApiModelProperty(value = "语言", required = true)
    private String lang;

}
