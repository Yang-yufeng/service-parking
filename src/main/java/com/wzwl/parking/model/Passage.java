package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ParkingLotMapper
 * @Description 通道信息
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Data
@TableName("t_parking_passage")
public class Passage {

  /**主键ID**/
  @TableId(type = IdType.UUID)
  private String id;

  /**企业ID**/
  private String companyId;

  /**停车场ID**/
  private String parkId;

  /**通道编号**/
  private Integer code;

  /**IP地址**/
  private String ip;

  /**使用类型**/
  private Integer useType;

  /**通道名**/
  private String name;

  /**区域编码**/
  private Integer areaCode;

}
