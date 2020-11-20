package com.wzwl.parking.vo;

import lombok.Data;

/**
 * @ClassName EnvironmentVO
 * @Description TODO
 * @Author huff
 * @Date 2020/11/19 17:31
 * @Version 1.0
 */
@Data
public class EnvironmentVO {

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
