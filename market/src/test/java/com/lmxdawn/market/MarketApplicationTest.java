package com.lmxdawn.market;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MarketApplicationTest {

    @Test
    void main() {
        String startAddTime = "2021-11-19 00:00:00";
        String endAddTime = "2021-11-30 00:00:00";
        if (!StringUtils.isBlank(startAddTime) && !StringUtils.isBlank(endAddTime)) {
            Date startAddTimeDate = null;
            Date endAddTimeDate = null;
            try {
                startAddTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startAddTime);
                endAddTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endAddTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(startAddTimeDate.getTime() / 1000);
            System.out.println(endAddTimeDate);
            // if (startAddTimeDate != null && endAddTimeDate != null) {
            //     booleanExpressions.add(QWithdraw.withdraw.addtime.goe(startAddTimeDate.getTime()));
            //     booleanExpressions.add(QWithdraw.withdraw.addtime.loe(endAddTimeDate.getTime()));
            // }
        }


    }
}