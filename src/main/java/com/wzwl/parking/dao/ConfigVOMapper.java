package com.wzwl.parking.dao;

import com.wzwl.parking.vo.ConfigVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ConfigMapper
 * @Description TODO
 * @Author yangwu
 * @Date 2020/11/5 18:25
 * @Version 1.0
 */
@Mapper
public interface ConfigVOMapper {


    /**
     * 根据配置值获取企业ID
     * @param configValue 配置值
     * @return
     */
    String getCompanyId(String configValue);


    /**
     * 根据企业获取配置信息
     * @param companyId 企业ID
     * @return
     */
    List<ConfigVO> getCompanyConfig(String companyId);
}
