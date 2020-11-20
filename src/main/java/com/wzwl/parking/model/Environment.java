package com.wzwl.parking.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Environment
 * @Description TODO
 * @Author huff
 * @Date 2020/11/18 14:51
 * @Version 1.0
 */
@Data
@TableName("t_parking_environment")
public class Environment {

    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 温度（℃）
     */
    private Integer temperature;

    /**
     * 湿度(RH)
     */
    private Integer humidity;

    /**
     * 二氧化碳浓度（PPM）
     */
    private Integer carbonDioxide;
}
