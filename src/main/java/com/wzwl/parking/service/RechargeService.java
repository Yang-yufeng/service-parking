package com.wzwl.parking.service;

import com.wzwl.parking.dto.RechargeRecordDTO;

/**
 * @ClassName RechargeService
 * @Description TODO
 * @Author huff
 * @Date 2020/11/13 16:04
 * @Version 1.0
 */
public interface RechargeService {


    /**
     * 保存固定车充值记录
     * @param dto
     */
    String saveRechargeRecord(RechargeRecordDTO dto);


}
