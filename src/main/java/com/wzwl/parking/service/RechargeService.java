package com.wzwl.parking.service;

import com.wzwl.parking.dto.RechargeRecordDTO;
import com.wzwl.parking.model.RechargeRecord;

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
     * @return
     */
    String saveRechargeRecord(RechargeRecordDTO dto);


    /*String saveRechargeRecord(String companyId,Integer parkId,Integer cardId, String orderNo,
                              Integer carType, Integer payChannel, Integer chargeMethod, Integer chargeNumber,
                              Integer amount, Integer freeNumber, String validFrom, String validTo,
                              String createTime,String remark, Integer rechargeType,Integer operationType,
                              String operator, String paySource);*/


}
