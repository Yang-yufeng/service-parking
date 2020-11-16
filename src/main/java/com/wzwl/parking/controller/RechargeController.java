package com.wzwl.parking.controller;

import com.wzwl.parking.dto.RechargeRecordDTO;
import com.wzwl.parking.model.RechargeRecord;
import com.wzwl.parking.service.RechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param
     */
    @RequestMapping("/reportRecords")
    public String getRechargeReportRecords(@RequestParam String companyId,@RequestParam Integer parkId,@RequestParam Integer cardId, @RequestParam String orderNo,
                                           @RequestParam Integer carType, @RequestParam Integer payChannel, @RequestParam Integer chargeMethod,@RequestParam Integer chargeNumber,
                                           @RequestParam Integer amount, @RequestParam Integer freeNumber, @RequestParam String validFrom, @RequestParam String validTo,
                                           @RequestParam String createTime,@RequestParam String remark,@RequestParam Integer rechargeType,@RequestParam Integer operationType,
                                           @RequestParam String operator,@RequestParam String paySource){

        /*RechargeRecordDTO dto = new RechargeRecordDTO(companyId,parkId,cardId,orderNo,carType,payChannel,chargeMethod,chargeNumber,amount,freeNumber,validFrom,validTo,
                createTime,remark,rechargeType,operationType,operator,paySource);*/
       return rechargeService.saveRechargeRecord( companyId, parkId, cardId,  orderNo,
                carType,  payChannel,  chargeMethod,  chargeNumber,
                amount,  freeNumber,  validFrom,  validTo,
                createTime, remark,  rechargeType, operationType,
                operator,  paySource);

    }

    /**
     * 固定车充值记录上报
     * @param
     */
    /*@RequestMapping("/reportRecords")
    public String getRechargeReportRecords(@RequestBody RechargeRecordDTO dto){

       return rechargeService.saveRechargeRecord(dto);

    }*/



}
