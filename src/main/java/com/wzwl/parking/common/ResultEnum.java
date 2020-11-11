package com.wzwl.parking.common;


/**
 * @ClassName ResultEnum
 * @Description 返回信息枚举类
 * @Author yangwu
 * @Date 2020/10/22 18:07
 * @Version 1.0
 */
public enum ResultEnum {


    /**通用错误**/

    /**成功**/
    SUCCESS(0,"操作成功"),

    /**停车场信息不存在**/
    PARKINGLOT_NOT_EXISTED(1001,"停车场不存在"),

    /**停车场信息已存在**/
    PARKINGLOT_ALREADY_EXISTED(1002,"停车场已存在"),

    /**无车辆入场信息**/
    CAR_IN_NOT_EXISTED(2001,"无车辆入场信息"),

    /**已有车辆入场信息**/
    CAR_IN_ALREADY_EXISTED(2002,"已有车辆入场信息"),

    /**没有找到配置信息**/
    CONFIG_NOT_EXISTED(8001,"没有找到配置信息"),

    /**APPKEY OR APPSECRET错误*/
    INCORRECTED_APPKEY_OR_APPSECRET(9001,"APPKEY OR APPSECRET错误"),


    /**服务器内部错误**/
    SERVER_ERROR(-1,"服务器内部错误");

    public int code;

    public String  msg;

    ResultEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
