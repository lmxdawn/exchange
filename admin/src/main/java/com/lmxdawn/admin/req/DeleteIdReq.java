package com.lmxdawn.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class DeleteIdReq {

    @NotNull(message = "ID必传")
    @Min(value = 1,message = "ID必传")
    @ApiModelProperty(value = "ID", required = true)
    private Long id;

}
