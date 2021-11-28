package com.lmxdawn.dubboapi.req.wallet;

import com.lmxdawn.dubboapi.req.ListPageDubboReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 币种配置
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class CoinConfQueryDubboReq extends ListPageDubboReq {

    @ApiModelProperty(value = "币种ID")
    private Long coinId;

    @ApiModelProperty(value = "协议ID")
    private Long protocolId;

    @ApiModelProperty(value = "状态（0: 全部，1：禁用，2：正常）")
    private Integer status;

}
