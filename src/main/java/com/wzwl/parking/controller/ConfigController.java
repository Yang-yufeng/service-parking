package com.wzwl.parking.controller;

import com.wzwl.parking.service.ConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName ConfigController
 * @Description 配置信息控制器
 * @Author yangwu
 * @Date 2020/11/5 18:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    /**
     * 根据配置值查询详细企业应用配置信息
     * @param configValue 配置值
     * @return
     */
    @RequestMapping("/get")
    public String getCompanyConfig(@RequestParam String configValue){
        return configService.getCompanyConfig(configValue);
    }


}
