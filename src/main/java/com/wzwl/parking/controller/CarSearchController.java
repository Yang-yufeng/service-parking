package com.wzwl.parking.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
    public String searchCar(@RequestParam(required = false) String plateNo,@RequestParam(defaultValue="1") Integer pageIndex,
                                  @RequestParam(defaultValue="10") Integer pageSize){
        return carSearchService.getCarLocalInfo(plateNo,pageIndex,pageSize);
    }

    /**
     * 长驻车管理
     * @param type  枚举类型
     * @param days  以天数计算
     * @param sortType  排序方式：0：升序，1：降序。默认是0
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/getLongParkingCarInfo")
    public String getLongParkingCarInfo(@RequestParam(required = false,defaultValue = "1") String type,@RequestParam(required = false) Integer days,@RequestParam(defaultValue = "0") Integer sortType,
                                @RequestParam(defaultValue="1") Integer pageIndex, @RequestParam(defaultValue="10") Integer pageSize){
        if (days!=null){
            return carSearchService.getLongParkingCarInfo(days,sortType,pageIndex,pageSize);
        }else {
            if (type.equals("1")){   //todo    此处有枚举参数处理
                days = 7;
            }
            return carSearchService.getLongParkingCarInfo(days,sortType,pageIndex,pageSize);
        }
    }

}
