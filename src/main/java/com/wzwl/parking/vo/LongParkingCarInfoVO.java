package com.wzwl.parking.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName LongParkingCarInfoVO
 * @Description 长驻车管理
 * @Author huff
 * @Date 2020/11/18 16:23
 * @Version 1.0
 */
@Data
public class LongParkingCarInfoVO{

    /**
     * 车牌号码
     */
    private String carNo;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 入场时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")//格式化日期
    private Date entryTime;

    /**
     * 入口名称
     */
    private String entryName;

    /**
     * 卡类型
     */
    private Integer cardType;

    /**
     * 已停放时长
     */
    private String parkingTime;

    /**
     * 抓拍图片
     */
    private String entryImage;



}
