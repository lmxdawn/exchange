package com.lmxdawn.other.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OnlineServiceInfoRes {

    @ApiModelProperty(value = "用户ID")
    private String userId;

}
