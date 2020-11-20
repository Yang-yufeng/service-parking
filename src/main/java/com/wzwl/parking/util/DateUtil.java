package com.wzwl.parking.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        Date date=new Date(timestamp*1000);
        return simpleDateFormat.format(date);
    }


    /**
     * 获取指定日期(yyyy-MM-dd)凌晨秒数
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
     * 将日期字符串转换为秒数
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
        return calendar.getTimeInMillis()/1000;
    }


    /**
     * 获取当天时间戳
     * @param hour 精确到小时 如9点10分，返回的是9点整的时间戳
     * @param minute 精确到分
     * @param second 精确到秒
     * @return
     */
    public static int getTodayStartTime(boolean hour,boolean minute,boolean second) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        if(!hour) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        if(!minute) {
            calendar.set(Calendar.MINUTE, 0);
        }
        if(!second) {
            calendar.set(Calendar.SECOND, 0);
        }
        return (int) (calendar.getTime().getTime() / 1000);
    }

    public static void main(String[] args) {
        System.out.println(getTimeStampByString("2020-11-19 20:44:44"));
        long sd=1605789884000L;
        Date dat=new Date(sd);
        System.out.println(dat);
        System.out.println("-------------");
        Date date = new Date();
        System.out.println(date);
        System.out.println(sdf.format(date));
        System.out.println(getTimeStampByString(sdf.format(date)));
        Date date1 = new Date(getTimeStampByString(sdf.format(date))*1000);
        System.out.println(date1);
        long a = 1604999829*1000;
        System.out.println(new Date(a));
    }

    /**
     * 根据时间差计算得到的停车时长
     * @param today
     * @param entryTime
     * @return  xx天xx小时
     */
    public static String getParkingTime(long today, Integer entryTime) {

        long time = today - entryTime;
        long days = time/86400;
        long hour = time%86400/3600;
        String result = "";
        if (days==0){
            result = hour+"小时";
        }else {
            result = days+"天"+hour+"小时";
        }
        return result;

    }
}
