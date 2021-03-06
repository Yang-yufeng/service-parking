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
     * 获取首页车位信息、环境监测、运营金额、出入统计、开闸统计的数据
     * @param companyId
     * @return
     */
    String getOverviewData(String companyId);

}
