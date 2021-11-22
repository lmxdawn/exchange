package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class RegionListInfoRes {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否是叶子节点（0：不是，1是）")
    private Integer isLeaf;

}
