package com.wzwl.parking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName CarSearchDTO
 * @Description 寻车管理
 * @Author huff
 * @Date 2020/11/19 19:21
 * @Version 1.0
 */
@Data
public class CarSearchDTO {

    @NotBlank(message = "公司ID不能为空")
    private String companyId;

    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 当前页
     */
    private Integer pageIndex = 1;

    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

}
