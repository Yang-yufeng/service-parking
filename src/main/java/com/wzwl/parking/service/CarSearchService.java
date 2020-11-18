package com.wzwl.parking.service;

import com.wzwl.parking.common.ResultEntity;

/**
 * @ClassName CarSearchService
 * @Description TODO
 * @Author huff
 * @Date 2020/11/17 15:16
 * @Version 1.0
 */


public interface CarSearchService {

    /**
     * 寻车管理,可根据车牌查询
     * @param plateNo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    String getCarLocalInfo(String plateNo, Integer pageIndex, Integer pageSize);
}
