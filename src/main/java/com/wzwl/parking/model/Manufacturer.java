package com.wzwl.parking.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 停车场系统厂商
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_manufacturer")
public class Manufacturer {

  /**主键ID**/
  @TableId(type = IdType.UUID)
  private String id;

  /**厂商名**/
  private String name;

  /**厂商编码**/
  private String code;

}
