package com.lmxdawn.trade.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 查询交易对列表
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class PairListPageReq extends ListPageReq {

    @ApiModelProperty(value = "计价币种ID")
    private Long coinId;

    @ApiModelProperty(value = "收藏的id")
    private String collect;

}
