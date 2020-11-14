package com.wzwl.parking.service;

import com.wzwl.parking.common.ResultEntity;

/**
 * @ClassName HomeService
 * @Description TODO
 * @Author huff
 * @Date 2020/11/14 10:17
 * @Version 1.0
 */
public interface HomeService {

    /**
     * 获取首页运营金额、出入统计、开闸统计的数据
     * @param companyId
     * @param parkId
     * @return
     */
    ResultEntity getData(String companyId, String parkId);
}
