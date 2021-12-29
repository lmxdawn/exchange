package com.lmxdawn.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class MemberCoinBalanceReq {

    @ApiModelProperty(value = "币种ID")
    @NotNull(message = "币种ID不能为空")
    @Min(value = 1, message = "币种ID不能为空")
    private Long coinId;

}
