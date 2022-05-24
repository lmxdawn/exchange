package com.lmxdawn.market.util;

import com.lmxdawn.market.vo.KLineDateTimeVo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页工具类
 */
public class KLineUtil {

    public static final Map<String, Long> timeMap = new HashMap<String, Long>() {{
        put("1min", 60L);
        put("5min", 5 * 60L);
        put("15min", 15 * 60L);
        put("30min", 30 * 60L);
        put("1hour", 60 * 60L);
        put("4hour", 4 * 60 * 60L);
        put("1day", 24 * 60 * 60L);
        put("1week", 7 * 24 * 60 * 60L);
        put("1month", 0L);
    }};

    // 需要减掉的时间，初始时间：1970-01-01 08:00:00
    public static final Map<String, Long> timeSubMap = new HashMap<String, Long>() {{
        put("1day", -8 * 60 * 60L); // 一天的零点需要减去8小时，因为时间戳从8点开始算的
        put("1week", 4 * 24 * 60 * 60 - 8 * 60 * 60L); // 每周的周一需要加上 4天，去掉8小时，因为时间戳是从周四开始的
    }};

    /**
     * 生成时间
     */
    public static KLineDateTimeVo createDateTime(String timeStr, Long time, Integer limit) {

        long prevTime = 0L;

        if (!timeMap.containsKey(timeStr)) {
            return new KLineDateTimeVo();
        }

        // 月份的处理方式不一样
        if ("1month".equals(timeStr)) {
            Calendar calendar = Calendar.getInstance();
            //将秒、微秒字段置为0
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            // 一月
            calendar.set(Calendar.DAY_OF_MONTH,1);
            time = calendar.getTime().getTime() / 1000;
            // 设置上一个时间
            calendar.add(Calendar.MONTH, -limit);
            prevTime = calendar.getTime().getTime() / 1000;

        } else {
            long nowTime = new Date().getTime() / 1000;
            Long timeValue = timeMap.get(timeStr);

            time = time == null || time <= 0 ? nowTime - (nowTime % timeValue) : time;
            if (timeSubMap.containsKey(timeStr)) {
                time = time + timeSubMap.get(timeStr);
            }
            prevTime = time - (limit * timeValue);
        }

        KLineDateTimeVo vo = new KLineDateTimeVo();
        vo.setTime(time);
        vo.setPrevTime(prevTime);

        return vo;
    }

}
