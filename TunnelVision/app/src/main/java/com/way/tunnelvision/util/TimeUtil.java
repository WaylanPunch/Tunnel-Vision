package com.way.tunnelvision.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pc on 2016/1/10.
 */
public class TimeUtil {
    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static String prettyTimeForDate(Date date) {
        String prettyStr = "";
        try {
            Date now = new Date();
            PrettyTime prettyTime = new PrettyTime(now, Locale.CHINESE);
            prettyStr = prettyTime.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            prettyStr = "";
        }
        return prettyStr;
    }

    public static String prettyTimeForDate(String dateStr) {
        String prettyStr = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(dateStr);
            Date now = new Date();
            PrettyTime prettyTime = new PrettyTime(now);
            prettyStr = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            prettyStr = "";
        }
        return prettyStr;
    }
}
