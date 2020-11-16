package com.wzwl.parking.constants;

/**
 * @ClassName TimeConstants
 * @Description TODO
 * @Author yangwu
 * @Date 2020/11/16 11:08
 * @Version 1.0
 */
public class TimeConstants {

    /**
     * 日趋势
     */
    public static final int DAY_TREND_TYPE = 1;

    /**
     * 周趋势
     */
    public static final int WEEK_TREND_TYPE  = 2;

    /**
     * 月趋势
     */
    public static final int MONTH_TREND_TYPE  = 3;

    /**
     * 年趋势
     */
    public static final int YEAR_TREND_TYPE = 4;

    /**
     *自定义时间
     */
    public static final int DEFINE_TREND_TYPE = 5;

    /**
     * 日趋势取点间隔,单位秒
     */
    public static final int DAY_TREND_POINTS_INTERVAL = 900;

    /**
     * 周趋势取点间隔,单位秒
     */
    public static final int WEEK_TREND_POINTS_INTERVAL = 7200;


    /**
     * 月趋势取点间隔,单位秒
     */
    public static final int MONTH_TREND_POINTS_INTERVAL = 6*3600;


    /**
     * 年趋势取点间隔,单位秒
     */
    public static final int YEAR_TREND_POINTS_INTERVAL = 86400*3;


}
