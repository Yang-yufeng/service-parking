package com.wzwl.parking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName ParkingDTO
 * @Description TODO
 * @Author huff
 * @Date 2020/11/19 16:27
 * @Version 1.0
 */
@Data
public class OverViewDTO {

    @NotBlank(message = "公司ID不能为空")
    private String companyId;
}
