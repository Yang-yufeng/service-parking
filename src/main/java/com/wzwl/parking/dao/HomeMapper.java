package com.wzwl.parking.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @ClassName HomeMapper
 * @Description TODO
 * @Author huff
 * @Date 2020/11/14 10:28
 * @Version 1.0
 */
@Mapper
public interface HomeMapper{

    /**
     * 获取首页月租车昨日及今日充值记录
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    Integer getMonthlyFee(@Param("companyId") String companyId, @Param("parkId")String parkId,
                                      @Param("before")long before,@Param("after")long after);

    /**
     * 获取首页昨日及今日缴费记录
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    Integer getDailyFee(@Param("companyId") String companyId, @Param("parkId")String parkId,
                                    @Param("before")long before,@Param("after")long after);
}
