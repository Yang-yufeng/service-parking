package com.wzwl.parking.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.service.HomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName MainController
 * @Description 首页
 * @Author huff
 * @Date 2020/11/14 10:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/show")
public class HomeController {

    @Resource
    private HomeService homeService;


    /**
     * 获取首页运营金额、出入统计、开闸统计的数据
     * @param companyId 公司id
     * @param parkId 车场id
     * @return
     */
    @RequestMapping("/getData")
    public ResultEntity getData(@RequestParam String companyId,@RequestParam(required = false) String parkId){
        return homeService.getData(companyId,parkId);
    }


    /**
     * 获得车位总数，空闲车位数，以及剩余车位总数
     * @return
     */
    @RequestMapping("/getParkingSpaceInfo")
    public ResultEntity getParkingSpaceInfo(){
        return homeService.getParkingSpaceInfo();
    }


}
