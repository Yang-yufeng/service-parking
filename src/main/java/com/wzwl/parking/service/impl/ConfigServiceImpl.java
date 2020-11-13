package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.ConfigVOMapper;
import com.wzwl.parking.vo.ConfigVO;
import com.wzwl.parking.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ConfigServiceImpl
 * @Description 配置信息接口实现
 * @Author yangwu
 * @Date 2020/11/5 18:22
 * @Version 1.0
 */
@Service
public class ConfigServiceImpl implements ConfigService {


    @Resource
    private ConfigVOMapper configVoMapper;

    @Override
    public String getCompanyConfig(String configValue) {
        String companyId = configVoMapper.getCompanyId(configValue);
        //后续要加上厂商ID查询条件
        if(companyId==null){
           ResultEntity result = new ResultEntity(ResultEnum.CONFIG_NOT_EXISTED);
           return  result.toString();
        }
        List<ConfigVO> list = configVoMapper.getCompanyConfig(companyId);
        if(list.size()==0){
           ResultEntity result = new ResultEntity(ResultEnum.CONFIG_NOT_EXISTED);
           return  result.toString();
        }
        JSONObject configJson = new JSONObject();
        //将list转换成json格式
        for(int i = 0;i<list.size();i++){
            ConfigVO cv = list.get(i);
            configJson.put(cv.getConfigName(),cv.getConfigValue());
        }
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        JSONObject json = new JSONObject();
        json.put("companyId", companyId);
        json.put("configInfo",configJson);
        result.setData(json);
        return result.toString();
        }
}
