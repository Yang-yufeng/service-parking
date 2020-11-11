package com.wzwl.parking.service;

/**
 * @ClassName ConfigService
 * @Description 配置信息接口
 * @Author yangwu
 * @Date 2020/11/5 18:22
 * @Version 1.0
 */
public interface ConfigService {

    /**
     * 根据配置值获取企业应用配置信息
     * @param configValue
     * @return
     */
    String getCompanyConfig(String configValue);
}
