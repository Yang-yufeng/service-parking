package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.service.CarSearchService;
import com.wzwl.parking.util.HttpUtil;
import com.wzwl.parking.vo.CarLocalInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CarSearchServiceImpl
 * @Description TODO
 * @Author huff
 * @Date 2020/11/17 15:17
 * @Version 1.0
 */

@Service
public class CarSearchServiceImpl implements CarSearchService {


    @Override
    public ResultEntity getCarLocalInfo(String plateNo, Integer pageIndex, Integer pageSize) {
        JSONObject params = new JSONObject();
        params.put("plateNo",plateNo);
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        JSONObject response = HttpUtil.doPost("http://localhost:80/car/getCarLocalInfo",params);   //todo  配置信息获取
        List<CarLocalInfoVO> array = JSONObject.parseArray(response.getJSONArray("data").toJSONString(), CarLocalInfoVO.class);
        //List<CarLocalInfoVo> array = JSONObject.parseArray(response.toJSONString(), CarLocalInfoVo.class);
        ResultEntity entity = new ResultEntity(ResultEnum.SUCCESS);
        entity.setData(array);
        return entity;
    }


}
