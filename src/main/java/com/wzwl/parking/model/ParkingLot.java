package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 停车场信息
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_lot")
public class ParkingLot {

  /**主键ID**/
  @TableId(type = IdType.UUID)
  private String id;

  /**企业ID**/
  private String companyId;

  /**停车场ID**/
  private String parkId;


  /**停车场名字**/
  private String name;

  /**总车位数**/
  private Integer totalSpace;

  /**已用车位数**/
  private Integer useSpace;

  /**空闲车位数**/
  private Integer freeSpace;

  /**限长**/
  private Integer limitLength;

  /**限宽**/
  private Integer limitWidth;

  /**限高**/
  private Integer limitHeight;

  /**经度**/
  private String lon;

  /**纬度**/
  private String lat;

  /**详细地址**/
  private String address;

  /**电话**/
  private String telephone;

  /**图片**/
  private String imgUrl;

  /**营业开始时间**/
  private Integer workStartTime;

  /**营业结束时间**/
  private Integer workEndTime;

  /**计费规则**/
  private String payRule;

  /**创建时间**/
  private Integer createTime;



}
