package com.wzwl.parking.controller;

import com.wzwl.parking.dto.OverViewDTO;
import com.wzwl.parking.service.HomeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName MainController
 * @Description 首页
 * @Author huff
 * @Date 2020/11/14 10:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @Resource
    private HomeService homeService;


    /**
     * 获取首页车位信息、环境监测、运营金额、出入统计、开闸统计的数据
     * @param dto
     * @return
     */
    @PostMapping(value = "/parking/getOverviewData")
    public String getOverviewData(@RequestBody @Validated OverViewDTO dto){
        return homeService.getOverviewData(dto.getCompanyId());
    }
}
