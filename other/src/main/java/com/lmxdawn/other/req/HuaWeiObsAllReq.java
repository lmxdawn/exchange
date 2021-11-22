package com.lmxdawn.other.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel
@Data
public class HuaWeiObsAllReq {
    @ApiModelProperty(value = "文件后缀列表", required = true)
    @NotNull(message = "至少传一个后缀")
    @Size(min = 1, message = "至少传一个后缀")
    private List<String> suffixs;
}
