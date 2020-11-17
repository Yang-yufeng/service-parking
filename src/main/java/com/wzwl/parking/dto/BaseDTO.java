package com.wzwl.parking.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName BaseDto
 * @Description TODO
 * @Author huff
 * @Date 2020/11/13 16:02
 * @Version 1.0
 */
@Data
public abstract class BaseDTO {

    @NotNull(message="appId不能为空")
    private String appId;

    @NotNull(message="key不能为空")
    private String key;

    @NotNull(message="parkId不能为空")
    private Integer parkId;

    @NotNull(message="serviceCode不能为空")
    private String serviceCode;

    @NotNull(message="ts不能为空")
    private String ts;

    @NotNull(message="reqId不能为空")
    private String reqId;

}
