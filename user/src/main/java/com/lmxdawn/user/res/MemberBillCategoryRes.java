package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemberBillCategoryRes {

    @ApiModelProperty(value = "账单分类名称")
    private String name;

    @ApiModelProperty(value = "账单分类标题")
    private String title;

}
