package com.lmxdawn.wsroute.res;

import lombok.Data;

@Data
public class LoadBalancingIpRes {

    // ip地址
    private String url;

    // http端口
    private String httpPort;

    // ws端口
    private String wsPort;

}
