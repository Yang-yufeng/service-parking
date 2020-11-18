package com.wzwl.parking.vo;

import lombok.Data;

/**
 * @ClassName LongParkingCarInfoVO
 * @Description 长驻车管理
 * @Author huff
 * @Date 2020/11/18 16:23
 * @Version 1.0
 */
@Data
public class LongParkingCarInfoVO {

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
    private Integer entryTime;

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
