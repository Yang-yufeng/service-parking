package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 厂商配置信息对应值
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_confg_value")
public class ConfgValue {

  /**主键**/
  @TableId(type = IdType.UUID)
  private String id;

  /**企业ID**/
  private String companyId;

  /**配置ID**/
  private String configId;

  /**配置对应值**/
  private String configValue;

}
