package com.wzwl.parking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzwl.parking.model.ParkingLot;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @ClassName ParkingLotMapper
 * @Description 停车场DAO
 * @Author yangwu
 * @Date 2020/11/3 19:18
 * @Version 1.0
 */
@Mapper
public interface ParkingLotMapper extends BaseMapper<ParkingLot> {

    /**
     * 根据企业ID和停车场ID查询停车场
     * @param companyId 企业ID
     * @param parkId 停车场ID
     * @return
     */
   ParkingLot getParkingLotById(String companyId, String parkId);



}
