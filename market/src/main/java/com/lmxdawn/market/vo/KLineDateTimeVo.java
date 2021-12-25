package com.lmxdawn.market.vo;

import lombok.Data;

/**
 * 时间分组
 */
@Data
public class KLineDateTimeVo {

    private Long time1min;
    private Long preTime1min;
    private Long time5min;
    private Long preTime5min;
    private Long time15min;
    private Long preTime15min;
    private Long time30min;
    private Long preTime30min;
    private Long time1hour;
    private Long preTime1hour;
    private Long time4hour;
    private Long preTime4hour;
    private Long time1day;
    private Long preTime1day;
    private Long time1week;
    private Long preTime1week;
    private Long time1month;
    private Long preTime1month;

}
