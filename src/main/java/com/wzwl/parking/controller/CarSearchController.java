package com.wzwl.parking.controller;


import com.wzwl.parking.dto.CarSearchDTO;
import com.wzwl.parking.dto.LongParkingDTO;
import com.wzwl.parking.service.CarSearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName FindCarController
 * @Description TODO
 * @Author huff
 * @Date 2020/11/17 15:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/v1")
public class CarSearchController {


    @Resource
    CarSearchService carSearchService;

    /**
     * 寻车管理,可根据车牌查询
     * @param dto
     * @return
     */
    @PostMapping("/parking/getCarLocalInfo")
    public String getCarLocalInfo(@RequestBody @Validated CarSearchDTO dto){
        return carSearchService.getCarLocalInfo(dto);
    }

    /**
     * 长驻车管理
     * @param dto
     * @return
     */
    @RequestMapping("/parking/getLongParkingInfo")
    public String getLongParkingInfo(LongParkingDTO dto){
        return carSearchService.getLongParkingCarInfo(dto);
    }

}
