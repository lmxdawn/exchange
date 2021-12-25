package com.lmxdawn.market.util;

import com.lmxdawn.market.vo.KLineDateTimeVo;

import java.util.Calendar;

/**
 * 分页工具类
 */
public class KLineUtil {

    /**
     * 生成时间
     */
    public static KLineDateTimeVo createDateTime() {
        KLineDateTimeVo vo = new KLineDateTimeVo();

        Calendar calendar = Calendar.getInstance();
        //将秒、微秒字段置为0
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        int minute = calendar.get(Calendar.MINUTE);
        // 1分钟
        long time1min = calendar.getTime().getTime() / 1000;
        vo.setTime1min(time1min);
        // 设置上一个时间
        vo.setPreTime1min(time1min - 60);

        // 5分钟
        calendar.set(Calendar.MINUTE, (minute / 5) * 5);
        long time5min = calendar.getTime().getTime() / 1000;
        vo.setTime5min(time5min);
        // 设置上一个时间
        vo.setPreTime5min(time5min - 5 * 60);

        // 15分钟
        calendar.set(Calendar.MINUTE, (minute / 15) * 15);
        long time15min = calendar.getTime().getTime() / 1000;
        vo.setTime15min(time15min);
        // 设置上一个时间
        vo.setPreTime15min(time15min - 15 * 60);

        // 30分钟
        calendar.set(Calendar.MINUTE, (minute / 30) * 30);
        long time30min = calendar.getTime().getTime() / 1000;
        vo.setTime30min(time30min);
        // 设置上一个时间
        vo.setPreTime30min(time30min - 30 * 60);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 1小时
        calendar.set(Calendar.MINUTE,0);
        long time1hour = calendar.getTime().getTime() / 1000;
        vo.setTime1hour(time1hour);
        // 设置上一个时间
        vo.setPreTime1hour(time1hour - 3600);

        // 4小时
        calendar.set(Calendar.HOUR_OF_DAY, (hour / 4) * 4);
        long time4hour = calendar.getTime().getTime() / 1000;
        vo.setTime4hour(time4hour);
        // 设置上一个时间
        vo.setPreTime4hour(time4hour - 4 * 3600);

        // 一天
        calendar.set(Calendar.HOUR_OF_DAY,0);
        long time1day = calendar.getTime().getTime() / 1000;
        vo.setTime1day(time1day);
        // 设置上一个时间
        vo.setPreTime1day(time1day - 86400);

        // 一周的星期一
        calendar.set(Calendar.DAY_OF_WEEK,2);
        long time1week = calendar.getTime().getTime() / 1000;
        vo.setTime1week(time1week);
        // 设置上一个时间
        vo.setPreTime1week(time1week - 7 * 86400);

        // 一月
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Long time1month = calendar.getTime().getTime() / 1000;
        vo.setTime1month(time1month);
        // 设置上一个时间
        calendar.add(Calendar.MONTH, -1);
        vo.setPreTime1month(calendar.getTime().getTime() / 1000);

        return vo;
    }

}
