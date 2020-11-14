package com.wzwl.parking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzwl.parking.model.CarRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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

    /**
     * 获取昨日及今日的获取进场上报记录
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    int getEntryCountByTime(@Param("companyId") String companyId, @Param("parkId")String parkId, @Param("before")long before, @Param("after")long after);

    /**
     * 获取昨日及今日的出场上报记录
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    int getExitCountByTime(@Param("companyId") String companyId, @Param("parkId")String parkId, @Param("before")long before, @Param("after")long after);

    /**
     * 入场开闸统计
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    Map<String, Integer> getEntryTypeCount(@Param("companyId") String companyId, @Param("parkId")String parkId,
                                           @Param("before")long before, @Param("after")long after);

    /**
     * 出场开闸统计
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    Map<String, Integer> getExitTypeCount(@Param("companyId") String companyId, @Param("parkId")String parkId,
                                          @Param("before")long before, @Param("after")long after);
}
