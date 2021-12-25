package com.lmxdawn.market;

import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MarketApplicationTest {

    @Test
    void main() {

        KLineDateTimeVo dateTime = KLineUtil.createDateTime();

        System.out.println("1分钟");
        ss(dateTime.getTime1min());
        ss(dateTime.getPreTime1min());

        System.out.println("5分钟");
        ss(dateTime.getTime5min());
        ss(dateTime.getPreTime5min());

        System.out.println("15分钟");
        ss(dateTime.getTime15min());
        ss(dateTime.getPreTime15min());

        System.out.println("30分钟");
        ss(dateTime.getTime30min());
        ss(dateTime.getPreTime30min());

        System.out.println("1小时");
        ss(dateTime.getTime1hour());
        ss(dateTime.getPreTime1hour());

        System.out.println("4小时");
        ss(dateTime.getTime4hour());
        ss(dateTime.getPreTime4hour());

        System.out.println("1天");
        ss(dateTime.getTime1day());
        ss(dateTime.getPreTime1day());

        System.out.println("1周");
        ss(dateTime.getTime1week());
        ss(dateTime.getPreTime1week());

        System.out.println("1月");
        ss(dateTime.getTime1month());
        ss(dateTime.getPreTime1month());


    }

    private static void ss(Long time) {
        String format = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(time * 1000);
        System.out.println(time);
        System.out.println(format);
    }
}