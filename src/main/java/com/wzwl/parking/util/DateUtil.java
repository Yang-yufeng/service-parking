package com.wzwl.parking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author huff
 * @Date 2020/11/14 11:22
 * @Version 1.0
 */
public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取指定日期(yyyy-MM-dd)凌晨毫秒数   //todo  待完善
     * @return
     */
    public static long getEarlyMorning(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,day,00,00,00);
        long millis = calendar.getTimeInMillis();
        return millis;
    }

    /**
     * 将日期字符串转换为毫秒数
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeStampByString(String dateStr){
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(getEarlyMorning(2020,9,2));
        System.out.println(getTimeStampByString("2020-09-02 00:00:00"));
    }


}
