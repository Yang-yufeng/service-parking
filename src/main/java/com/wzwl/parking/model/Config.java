package com.wzwl.parking.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 厂商配置信息
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_config")
public class Config {

  /**主键ID**/
  @TableId(type = IdType.UUID)
  private String id;

  /**厂商ID**/
  private String manuId;

  /**配置名**/
  private String configName;

  /**配置类型**/
  private Integer configType;

  /**默认值**/
  private String defaultValue;

}
