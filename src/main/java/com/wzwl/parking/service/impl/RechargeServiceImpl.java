package com.wzwl.parking.service.impl;

import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.RechargeMapper;
import com.wzwl.parking.dto.RechargeRecordDTO;
import com.wzwl.parking.model.RechargeRecord;
import com.wzwl.parking.service.RechargeService;
import com.wzwl.parking.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName RechargeServiceImpl
 * @Description TODO
 * @Author huff
 * @Date 2020/11/13 16:07
 * @Version 1.0
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeMapper rechargeMapper;

    /*@Override
    public String saveRechargeRecord(String companyId,Integer parkId,Integer cardId, String orderNo,
                                     Integer carType, Integer payChannel, Integer chargeMethod, Integer chargeNumber,
                                     Integer amount, Integer freeNumber, String validFrom, String validTo,
                                     String createTime,String remark, Integer rechargeType,Integer operationType,
                                     String operator, String paySource) {

        RechargeRecord model = new RechargeRecord();
        //BeanUtils.copyProperties(dto,model);
        model.setCompanyId(companyId);
        model.setParkId(parkId);
        model.setCardId(cardId);
        model.setOrderNo(orderNo);
        model.setCarType(carType);
        model.setPayChannel(payChannel);
        model.setChargeMethod(chargeMethod);
        model.setChargeNumber(chargeNumber);
        model.setAmount(amount);
        model.setFreeNumber(freeNumber);
        model.setValidFrom(DateUtil.getTimeStampByString(validFrom));
        model.setValidTo(DateUtil.getTimeStampByString(validTo));
        model.setCreateTime(DateUtil.getTimeStampByString(createTime));
        model.setRemark(remark);
        model.setRechargeType(rechargeType);
        model.setOperationType(operationType);
        model.setOperator(operator);
        model.setPaySource(paySource);
        int flag = rechargeMapper.insert(model);
        ResultEntity result;
        if (flag==1){
            result =  new ResultEntity(ResultEnum.SUCCESS);
        }else {
            result = new ResultEntity(ResultEnum.SERVER_ERROR);
        }
        return result.toString();
    }*/

    @Override
    public String saveRechargeRecord(RechargeRecordDTO dto) {
        RechargeRecord model = new RechargeRecord();
        BeanUtils.copyProperties(dto,model);
        model.setValidFrom(DateUtil.getTimeStampByString(dto.getValidFrom()));
        model.setValidTo(DateUtil.getTimeStampByString(dto.getValidTo()));
        model.setCreateTime(DateUtil.getTimeStampByString(dto.getCreateTime()));

        int flag = rechargeMapper.insert(model);
        ResultEntity result;
        if (flag==1){
            result =  new ResultEntity(ResultEnum.SUCCESS);
        }else {
            result = new ResultEntity(ResultEnum.SERVER_ERROR);
        }
        return result.toString();
    }

}
