package com.wzwl.parking.controller;

import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.service.CarSearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName FindCarController
 * @Description TODO
 * @Author huff
 * @Date 2020/11/17 15:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/carSearch")
public class CarSearchController {


    @Resource
    CarSearchService carSearchService;

    /**
     * 寻车管理,可根据车牌查询
     * @param plateNo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/getCarLocalInfo")
    public ResultEntity searchCar(@RequestParam(required = false) String plateNo,@RequestParam(defaultValue="1") Integer pageIndex,
                                  @RequestParam(defaultValue="10") Integer pageSize){
        return carSearchService.getCarLocalInfo(plateNo,pageIndex,pageSize);
    }

}
