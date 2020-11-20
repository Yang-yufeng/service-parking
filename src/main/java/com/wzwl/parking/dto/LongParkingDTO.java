package com.wzwl.parking.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LongParkingDTO
 * @Description 常驻车管理请求参数
 * @Author huff
 * @Date 2020/11/19 20:16
 * @Version 1.0
 */
@Data
public class LongParkingDTO {

    @NotBlank(message = "公司ID不能为空")
    private String companyId;

    /**
     * 查看一段时间内的长驻车（1：周；2：月，默认为1）
     */
    private Integer type = 1;

    /**
     * 查看xx天内的常驻车（优先级比type高）
     */
    private Integer days;

    /**
     * 按停放时长排序：0：升序，1：降序。默认是1
     */
    private Integer sortType = 1;

    /**
     * 当前页
     */
    private Integer pageIndex = 1;

    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

}
