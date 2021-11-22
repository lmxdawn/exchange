package com.lmxdawn.user.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UserMsgReq {

    /**
     * 接收者ID
     */
    @ApiModelProperty(value = "接收者ID", required = true)
    @NotNull(message = "Recipient ID is required")
    private Long receiveId;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型", required = true)
    @NotNull(message = "Message type is required")
    private Integer msgType;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容", required = true)
    @NotEmpty(message = "Message content is required")
    private String msgContent;


}
