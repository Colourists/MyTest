package com.gamecat.baselib.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bch on 2020/5/15
 */
public class DateUtil {
    /**
     * 单位:秒
     */
    public static String formatDateYYYYMMDD(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time * 1000);
        return df.format(date);
    }

    public static String formatDateYYYYMMDDToDate(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time * 1000);
        return df.format(date);
    }

    public static String formatDateYYYYMMDDWithDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String formatDateTopic1(long time) {
        SimpleDateFormat df = new SimpleDateFormat("EEE,MMM d,yyyy \nh:mm a", Locale.ENGLISH);
        Date date = new Date(time);
        return df.format(date);
    }

    public static String formatDateTopic(long time) {
        SimpleDateFormat df = new SimpleDateFormat("EEE,MMM d,yyyy \nh:mm a", Locale.ENGLISH);
        Date date = new Date(time * 1000L);
        return df.format(date);
    }

    public static String formatDate(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time * 1000);
        return df.format(date);
    }

    public static Date updateData(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, i);// +i天 月份自动变化
        return c.getTime();
    }

    public static Date addOneData(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);// +1天 月份自动变化
        return c.getTime();
    }

    public static Date subtractOneData(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);// +1天 月份自动变化
        return c.getTime();
    }

    //获取今天的时间戳
    public static long getTodayTimeMillis() {
        long now = System.currentTimeMillis();
        return now - now % (24 * 60 * 60 * 1000);
    }

    //获取今天的时间戳
    public static long getDateTimeMillis(long timeMillis) {
        return timeMillis - timeMillis % (24 * 60 * 60 * 1000);
    }

    public static boolean isYesterday(long timestamp) {
        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        c.add(Calendar.DAY_OF_MONTH, -1);
        long firstOfDay = c.getTimeInMillis(); // 昨天最早时间

        c.setTimeInMillis(timestamp);
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND); // 指定时间戳当天最早时间

        return firstOfDay == c.getTimeInMillis();
    }

    /**
     * 返回true 代表是今天之前的
     *
     * @param timestamp
     * @return
     */
    public static boolean isBeforeDays(long timestamp) {
        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        long firstOfDay = c.getTimeInMillis(); // 当天最早时间
//给的时间戳小于今天最早的时间戳.在今天之前
        return timestamp < firstOfDay;
    }

    private static void clearCalendar(Calendar c, int... fields) {
        for (int f : fields) {
            c.set(f, 0);
        }
    }

}
