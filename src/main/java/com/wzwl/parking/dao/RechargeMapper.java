package com.wzwl.parking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzwl.parking.model.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName RechargeMapper
 * @Description TODO
 * @Author huff
 * @Date 2020/11/13 16:14
 * @Version 1.0
 */
@Mapper
public interface RechargeMapper extends BaseMapper<RechargeRecord> {

    /**
     * 获取首页月租车昨日及今日充值记录
     * @param companyId
     * @param parkId
     * @param before
     * @param after
     * @return
     */
    int getMonthlyFee(@Param("companyId") String companyId, @Param("parkId")String parkId,
                      @Param("before")long before, @Param("after")long after);


}
