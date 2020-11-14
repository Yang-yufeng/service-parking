package com.wzwl.parking.controller;

import com.wzwl.parking.dto.RechargeRecordDTO;
import com.wzwl.parking.service.RechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName RechargeController
 * @Description 固定车充值
 * @Author huff
 * @Date 2020/11/13 11:11
 * @Version 1.0
 */

@RestController
@RequestMapping("/recharge")
public class RechargeController {


    @Resource
    private RechargeService rechargeService;

    /**
     * 固定车充值记录上报
     * @param dto
     */
    @RequestMapping("/reportRecords")
    public String getRechargeReportRecords(@RequestBody @Validated RechargeRecordDTO dto){

       return rechargeService.saveRechargeRecord(dto);

    }


}
