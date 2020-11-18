package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.CarRecordMapper;
import com.wzwl.parking.model.CarRecord;
import com.wzwl.parking.service.CarSearchService;
import com.wzwl.parking.util.DateUtil;
import com.wzwl.parking.util.HttpUtil;
import com.wzwl.parking.vo.CarLocalInfoVO;
import com.wzwl.parking.vo.LongParkingCarInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    CarRecordMapper carRecordMapper;

    @Override
    public String getCarLocalInfo(String plateNo, Integer pageIndex, Integer pageSize) {
        JSONObject params = new JSONObject();
        params.put("plateNo",plateNo);
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        JSONObject response = HttpUtil.doPost("http://localhost:80/car/getCarLocalInfo",params);   //todo  配置信息获取
        List<CarLocalInfoVO> array = JSONObject.parseArray(response.getJSONArray("data").toJSONString(), CarLocalInfoVO.class);
        //List<CarLocalInfoVo> array = JSONObject.parseArray(response.toJSONString(), CarLocalInfoVo.class);
        ResultEntity entity = new ResultEntity(ResultEnum.SUCCESS);
        entity.setData(array);
        return entity.toString();
    }

    @Override
    public String getLongParkingCarInfo(Integer days, Integer sortType, Integer pageIndex, Integer pageSize) {

        //todo  天数枚举暂时不处理


        //当前时间秒数
        long today = DateUtil.getTimeStampByString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        long before = today - 86400*days;


        QueryWrapper<CarRecord> wrapper = new QueryWrapper<>();
        wrapper.lt("entry_time",before);
        wrapper.eq("exit_time",0);
        if (sortType==1){
            wrapper.orderByDesc("entry_time");
        }else {
            wrapper.orderByAsc("entry_time");
        }
        IPage<CarRecord> recordPage = new Page<>(pageIndex,pageSize);
        recordPage = carRecordMapper.selectPage(recordPage,wrapper);
        List<CarRecord> list = recordPage.getRecords();
        List<LongParkingCarInfoVO> data = new ArrayList<>();
        list.stream().forEach(e->{
            LongParkingCarInfoVO vo = new LongParkingCarInfoVO();
            BeanUtils.copyProperties(e,vo);
            vo.setParkingTime(DateUtil.getParkingTime(today,vo.getEntryTime()));
            data.add(vo);
        });

        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        //总记录数
        long total = recordPage.getTotal();
        //当前页记录数
        int current = list.size();
        JSONObject json = new JSONObject();
        json.put("total", total);
        if(current>0) {
            json.put("carRecordList", list);
        }
        result.setData(data);
        return  result.toString();


    }


}
