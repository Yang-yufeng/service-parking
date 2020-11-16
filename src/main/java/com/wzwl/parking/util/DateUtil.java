package com.wzwl.parking.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * @ClassName DateUtil
 * @Description 日期处理工具类
 * @Author huff
 * @Date 2020/11/14 11:22
 * @Version 1.0
 */
public class DateUtil {

    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static int dateToStamp(String dateString, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Date date=simpleDateFormat.parse(dateString);
        return (int) (date.getTime() / 1000);
    }

    public static String stampToDate(long timestamp, String format) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Date date=new Date(timestamp);
        return simpleDateFormat.format(date);
    }


    /**
     * 获取指定日期(yyyy-MM-dd)凌晨毫秒数
     *
     * @return
     */
    public static long getEarlyMorning(int year, int month, int day) {
        Calendar calendar=Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day, 00, 00, 00);
        long millis=calendar.getTimeInMillis() / 1000;
        return millis;
    }

    /**
     * 将日期字符串转换为毫秒数
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeStampByString(String dateStr) {
        Calendar calendar=Calendar.getInstance();
        try {
            Date date=sdf.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * @return 当天的零点时间戳
     */
    public static int getTodayStartTime() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTime().getTime() / 1000);
    }

}
