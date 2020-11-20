package com.wzwl.parking.service;

import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.dto.CarSearchDTO;
import com.wzwl.parking.dto.LongParkingDTO;

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
     * @param dto
     * @return
     */
    String getCarLocalInfo(CarSearchDTO dto);

    /**
     * 长驻车管理
     * @param dto
     * @return
     */
    String getLongParkingCarInfo(LongParkingDTO dto);
}
