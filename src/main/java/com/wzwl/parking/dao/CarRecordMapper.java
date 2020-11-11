package com.wzwl.parking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzwl.parking.model.CarRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName CarMapper
 * @Description 车辆记录DAO
 * @Author yangwu
 * @Date 2020/11/3 16:33
 * @Version 1.0
 */
@Mapper
public interface CarRecordMapper extends BaseMapper<CarRecord> {

    /**
     * 根据车牌号和入场时间查询记录
     * @param carNo 车牌号
     * @param cardNo 卡号
     * @param entryTime 入场时间
     * @param companyId  企业ID
     * @return
     */
    CarRecord getCarRecordByEntryTime(@Param("carNo") String carNo,@Param("cardNo")String cardNo, int entryTime,
    String companyId);


}
