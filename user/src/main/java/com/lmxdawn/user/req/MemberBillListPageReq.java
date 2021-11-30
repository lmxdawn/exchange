package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class MemberBillListPageReq extends ListPageReq{

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "币种ID")
    private Long coinId;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "账单分类")
    private String category;

}
