package com.lmxdawn.market;

import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

class MarketApplicationTest {

    @Test
    void main() {


        KLineUtil.timeMap.keySet().forEach(timeStr -> {

            KLineDateTimeVo timeVo = KLineUtil.createDateTime(timeStr, null, 1);

            System.out.println(timeStr);
            ss(timeVo.getTime());
            ss(timeVo.getPrevTime());
        });

    }

    private static void ss(Long time) {
        String format = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(time * 1000);
        System.out.println(time);
        System.out.println(format);
    }
}