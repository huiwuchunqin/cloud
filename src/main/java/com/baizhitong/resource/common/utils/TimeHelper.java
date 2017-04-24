package com.baizhitong.resource.common.utils;

import java.util.Calendar;
import java.util.Date;

import com.baizhitong.utils.DateUtils;

public class TimeHelper {
    /**
     * 得到前一天 ()<br>
     * 
     * @return
     */
    public static Integer getPreDate() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.DAY_OF_YEAR, -1);
        Date date = aCalendar.getTime();
        return Integer.parseInt(DateUtils.formatDate(date, "yyyyMMdd"));
    }
}
