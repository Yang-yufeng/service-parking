package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 停车场商和企业绑定信息
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_binding")
public class Binding {

  /**主键ID**/
  @TableId(type = IdType.UUID)
  private String id;

  /**企业ID**/
  private String companyId;

  /**厂商ID**/
  private String manuId;

}
