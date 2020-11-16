package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 车辆记录
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_car_record")
public class CarRecord {

  @TableId(type = IdType.UUID)
  /**主键ID**/
  private String id;

  /**企业ID**/
  private String companyId;

  /**停车场ID**/
  private String parkId;

  /**车牌号高*/
  private String carNo;

  /**无牌车卡号**/
  private String cardNo;

  /**入场时间**/
  private Integer entryTime;

  /**入场图片URL**/
  private String entryImage;

  /**入场放行类型**/
  private Integer entryPassType;

  /**入场放行说明**/
  private String entryPassRemark;

  /**入口名**/
  private String entryName;

  /**卡类型**/
  private Integer cardType;

  /**出场时间**/
  private Integer exitTime;

  /**出场图片URL**/
  private String exitImage;

  /**出场放行类型**/
  private Integer exitPassType;

  /**出场放行说明**/
  private String exitPassRemark;

  /**出口名**/
  private String exitName;

  /**支付时间**/
  private Integer payTime;

  /**支付金额，单位：分**/
  private Integer payMoney;

  /**停车场总车位**/
  private Integer totalSpace;

  /**已用车位数**/
  private Integer useSpace;

  /**空闲车位数**/
  private Integer freeSpace;



}
