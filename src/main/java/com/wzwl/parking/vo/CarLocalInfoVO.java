package com.wzwl.parking.vo;

import lombok.Data;

/**
 * @ClassName CarLocalInfoVo
 * @Description 寻车管理实体
 * @Author huff
 * @Date 2020/11/17 15:51
 * @Version 1.0
 */
@Data
public class CarLocalInfoVO {

    /**
     * 停车场ID
     */
    private Integer parkId;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 区域
     */
    private String area;

    /**
     * 停车位编号
     */
    private String spaceNo;

    /**
     * 楼层名称
     */
    private String floorName;

    /**
     * 停放时间
     */
    private String inTime;

    /**
     * 停放时长(分)
     */
    private String parkTime;

    /**
     * 车辆图片
     */
    private String carImage;



}
