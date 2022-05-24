package com.lmxdawn.market;

import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

class TimeTest {

    @Test
    void time() {


        long time = new Date().getTime() / 1000;

        long i = 4 * 24 * 60 * 60 - 8 * 60 * 60;
        long s = time - (time % (7 * 24 * 60 * 60L)) + i;

        // System.out.println(24 * 60 * 60L);
        ss(s);

        KLineDateTimeVo timeVo = KLineUtil.createDateTime("1week", null, 1);

        ss(timeVo.getTime());

    }

    private static void ss(Long time) {
        String format = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(time * 1000);
        System.out.println(time);
        System.out.println(format);
    }

}