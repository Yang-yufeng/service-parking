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
    String getData(String companyId, String parkId);

    /**
     * 获得车位总数，空闲车位数，以及剩余车位总数
     * @return
     */
    String getParkingSpaceInfo();
}
