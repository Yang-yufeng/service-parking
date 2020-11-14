package com.wzwl.parking.service.impl;

import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.RechargeMapper;
import com.wzwl.parking.dto.RechargeRecordDTO;
import com.wzwl.parking.model.RechargeRecord;
import com.wzwl.parking.service.RechargeService;
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

    @Override
    public String saveRechargeRecord(RechargeRecordDTO dto) {

        RechargeRecord model = new RechargeRecord();
        BeanUtils.copyProperties(dto,model);
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
