package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.CarRecordMapper;
import com.wzwl.parking.dao.EnvironmentMapper;
import com.wzwl.parking.dao.ParkingLotMapper;
import com.wzwl.parking.dao.RechargeMapper;
import com.wzwl.parking.model.Environment;
import com.wzwl.parking.service.HomeService;
import com.wzwl.parking.util.DateUtil;
import com.wzwl.parking.util.HttpUtil;
import com.wzwl.parking.vo.EnvironmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomeServiceImpl
 * @Description TODO
 * @Author huff
 * @Date 2020/11/14 10:18
 * @Version 1.0
 */

@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private CarRecordMapper carRecordMapper;

    @Resource
    private RechargeMapper rechargeMapper;

    @Resource
    private EnvironmentMapper environmentMapper;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public String getOverviewData(String companyId) {

        JSONObject resultData = new JSONObject();
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);

        //计算出今日凌晨、昨日凌晨、明日凌晨的时间戳(用于比较今日较昨日的上升率)
        Calendar now = Calendar.getInstance();
        long today = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        now.add(Calendar.DATE,-1);
        long yesterday = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        now.add(Calendar.DATE,2);
        long tomorrow = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

        //获取月租车充值记录
        JSONObject rechargeCount =  getRechargeData(companyId, percentFormat, today, yesterday, tomorrow);
        resultData.put("rechargeCount",rechargeCount);

        //获取车辆出入统计信息
        JSONObject carEntryAndExitCount = getCarInOutData(companyId, percentFormat, today, yesterday, tomorrow);
        resultData.put("carEntryAndExitCount",carEntryAndExitCount);

        //获取开闸统计信息
        JSONObject openGateCount = getJsonObject(companyId, percentFormat, today, tomorrow);
        resultData.put("openGateCount",openGateCount);

        //获取车位信息
        JSONObject parkingSpaceCount = getParkingSpaceInfo(companyId);
        resultData.put("parkingSpaceCount",parkingSpaceCount);

        //获取环境监测信息
        JSONObject environmentDetectCount = getEnvironmentInfo(companyId);
        resultData.put("environmentDetectCount",environmentDetectCount);

        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        result.setData(resultData);
        return result.toString();
    }


    /**
     * 获得车位总数，空闲车位数，以及剩余车位总数
     * @param companyId
     * @return
     */
    public JSONObject getParkingSpaceInfo(String companyId) {

        JSONObject params = new JSONObject();
        params.put("companyId",companyId);
        JSONObject response = HttpUtil.doPost("http://localhost:80/car/getParkingSpaceInfo",params);   //todo  配置信息获取
        JSONObject result = new JSONObject();
        //请求成功
        if (response.getInteger("code")!=0){
            result.put("totalSpaceNum",0);
            result.put("freeSpaceNum",0);
            result.put("occupySpaceNum",0);
            return result;
        }
        JSONArray array = response.getJSONObject("data").getJSONArray("data");   //todo  统一定义,尽量不写死
        int totalSpaceNum = 0;
        int freeSpaceNum = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            totalSpaceNum+=jsonObject.getIntValue("totalSpaceNum");
            freeSpaceNum+=jsonObject.getIntValue("freeSpaceNum");
        }
        result.put("totalSpaceNum",totalSpaceNum);
        result.put("freeSpaceNum",freeSpaceNum);
        result.put("occupySpaceNum",totalSpaceNum-freeSpaceNum);
        return result;
    }

    /**
     * 获得环境监测中温度、湿度、二氧化碳浓度
     * @param companyId
     * @return
     */
    public JSONObject getEnvironmentInfo(String companyId) {
        QueryWrapper<Environment> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id",companyId);
        wrapper.orderByDesc("create_time");
        wrapper.last("limit 1");
        Environment model = environmentMapper.selectOne(wrapper);
        if (model==null){
            return null;
        }
        EnvironmentVO vo = new EnvironmentVO();
        BeanUtils.copyProperties(model,vo);
        JSONObject result = (JSONObject) JSON.toJSON(vo);
        return result;
    }


    /**
     * 获取运营金额数据（今日临停费用、今日月租车费用以及两者相较昨日的上升率）
     * @param companyId
     * @param percentFormat
     * @param today
     * @param yesterday
     * @param tomorrow
     * @return
     */
    private JSONObject getRechargeData(String companyId, NumberFormat percentFormat, long today, long yesterday, long tomorrow) {
        JSONObject recharge = new JSONObject();
        int yesterdayMonthlyFee = rechargeMapper.getMonthlyFee(companyId, yesterday, today)/100;
        int todayMonthlyFee = rechargeMapper.getMonthlyFee(companyId, today, tomorrow)/100;

        //获取今日缴费记录（昨日及今日）
        int yesterdayTemporaryFee = carRecordMapper.getDailyFee(companyId, yesterday, today)/100;
        int todayTemporaryFee = carRecordMapper.getDailyFee(companyId, today, tomorrow)/100;
        //获得今日临停费用上升比率
        double todayTemporaryFeeRate;
        if ((yesterdayMonthlyFee+yesterdayTemporaryFee)==0){
            todayTemporaryFeeRate = 100.00;
        }else {
            todayTemporaryFeeRate = new BigDecimal((float)(todayMonthlyFee+todayTemporaryFee-yesterdayMonthlyFee-yesterdayTemporaryFee)/(yesterdayMonthlyFee+yesterdayTemporaryFee))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        //获得今日月租车费用上升比率
        double todayMonthlyFeeRate;
        if (yesterdayMonthlyFee==0){
            todayMonthlyFeeRate = 100.00;
        }else {
            todayMonthlyFeeRate = new BigDecimal((float)(todayMonthlyFee-yesterdayMonthlyFee)/(yesterdayMonthlyFee))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        recharge.put("todayTemporaryFee",todayMonthlyFee+todayTemporaryFee);
        recharge.put("todayMonthlyFee",todayMonthlyFee);
        recharge.put("todayTemporaryFeeRate", percentFormat.format(todayTemporaryFeeRate));
        recharge.put("todayMonthlyFeeRate", percentFormat.format(todayMonthlyFeeRate));
        return recharge;
    }

    /**
     * 获得概览里车辆出入统计数据（今日入车、出车、车位饱和率以及相较昨日的比率）
     * @param companyId
     * @param percentFormat
     * @param today
     * @param yesterday
     * @param tomorrow
     * @return
     */
    private JSONObject getCarInOutData(String companyId, NumberFormat percentFormat, long today, long yesterday, long tomorrow) {
        JSONObject carEntryAndExitCount = new JSONObject();
        //获取进场上报记录（昨日及今日）
        int yesterdayEntryCount = carRecordMapper.getEntryCountByTime(companyId, yesterday, today);
        int todayEntryCount = carRecordMapper.getEntryCountByTime(companyId, today, tomorrow);
        //获取今日较昨日入车数量百分比
        double todayEntryRate;
        if (yesterdayEntryCount==0){
            todayEntryRate = 100.00;
        }else {
            todayEntryRate = new BigDecimal((float)(todayEntryCount-yesterdayEntryCount)/yesterdayEntryCount)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayEntryCount",todayEntryCount);
        carEntryAndExitCount.put("todayEntryRate", percentFormat.format(todayEntryRate));
        //获取出场上报记录（昨日及今日）
        int yesterdayExitCount = carRecordMapper.getExitCountByTime(companyId, yesterday, today);
        int todayExitCount = carRecordMapper.getExitCountByTime(companyId, today, tomorrow);
        //获取今日较昨日入车数量百分比
        double todayExitRate;
        if (yesterdayExitCount==0){
            todayExitRate = 100.00;
        }else {
            todayExitRate = new BigDecimal((float)(todayExitCount-yesterdayExitCount)/yesterdayExitCount)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayExitCount",todayExitCount);
        carEntryAndExitCount.put("todayExitRate", percentFormat.format(todayExitRate));
        //获取停车周转率
        Integer yesterdayHoldCount = carRecordMapper.getParkingSpaceUse(companyId, yesterday,System.currentTimeMillis()/1000-86400);
        Integer todayHoldCount = carRecordMapper.getParkingSpaceUse(companyId, today,System.currentTimeMillis()/1000);
        Integer parkingSpaceNum = carRecordMapper.getParkingSpaceCount(companyId, today,System.currentTimeMillis()/1000);
        double todayParkingSpaceRate = 0;
        double todayThanYesterdayRate = 0;
        if (parkingSpaceNum==null||parkingSpaceNum==0){
            todayParkingSpaceRate = 100.00;
            todayThanYesterdayRate = 100.00;
        }else {
            todayParkingSpaceRate = new BigDecimal((float)todayHoldCount/parkingSpaceNum)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            todayThanYesterdayRate = new BigDecimal((float)(todayHoldCount-yesterdayHoldCount)/parkingSpaceNum)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayParkingSpaceRate", percentFormat.format(todayParkingSpaceRate));
        carEntryAndExitCount.put("todayThanYesterdayRate", percentFormat.format(todayThanYesterdayRate));

        return carEntryAndExitCount;
    }

    /**
     * 获得概览里开闸统计数据（正常、异常、免费以及相较总车位数的比率）
     * @param companyId
     * @param percentFormat
     * @param today
     * @param tomorrow
     * @return
     */
    private JSONObject getJsonObject(String companyId, NumberFormat percentFormat, long today, long tomorrow) {
        JSONObject openGateCount = new JSONObject();
        List<Map<String,Object>> todayExitTypeCount = carRecordMapper.getExitTypeCount(companyId, today, tomorrow);
        List<Map<String,Object>> todayEntryTypeCount = carRecordMapper.getEntryTypeCount(companyId, today, tomorrow);

        int normalTypeCount = 0;
        int unNormalTypeCount = 0;
        int freeTypeCount = 0;
        int passTypeCountNum = 0;
        Iterator exitIt = todayExitTypeCount.listIterator();
        while (exitIt.hasNext()){
            Map<String,Object> map = (Map<String, Object>) exitIt.next();
            Integer exit_pass_type = (Integer) map.get("exit_pass_type");
            if (exit_pass_type!=null){
                if (exit_pass_type==0){
                    normalTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }else if (exit_pass_type==1){
                    freeTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }else if (exit_pass_type==2){
                    unNormalTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }
            }
        }
        Iterator entryIt = todayEntryTypeCount.listIterator();
        while (entryIt.hasNext()){
            Map<String,Object> map = (Map<String, Object>) entryIt.next();
            Integer entry_pass_type = (Integer) map.get("entry_pass_type");
            if (entry_pass_type!=null){
                if (entry_pass_type==0){
                    normalTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }else if (entry_pass_type==1){
                    freeTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }else if (entry_pass_type==2){
                    unNormalTypeCount+=((BigDecimal)map.get("sum")).intValue();
                }
            }
        }
        passTypeCountNum = normalTypeCount+unNormalTypeCount+freeTypeCount;
        openGateCount.put("normalTypeCount",normalTypeCount);
        openGateCount.put("unNormalTypeCount",unNormalTypeCount);
        openGateCount.put("freeTypeCount",freeTypeCount);
        openGateCount.put("passTypeCountNum",passTypeCountNum);
        double normalTypeCountRate;
        double unNormalTypeCountRate;
        double freeTypeCountRate;
        if (passTypeCountNum==0){
            normalTypeCountRate = 0;
            unNormalTypeCountRate = 0;
            freeTypeCountRate = 0;
        }else {
            normalTypeCountRate = new BigDecimal((float)normalTypeCount/passTypeCountNum)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            unNormalTypeCountRate = new BigDecimal((float)unNormalTypeCount/passTypeCountNum)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            freeTypeCountRate = new BigDecimal((float)freeTypeCount/passTypeCountNum)
                    .setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        openGateCount.put("normalTypeCountRate", percentFormat.format(normalTypeCountRate));
        openGateCount.put("unNormalTypeCountRate", percentFormat.format(unNormalTypeCountRate));
        openGateCount.put("freeTypeCountRate", percentFormat.format(freeTypeCountRate));
        return openGateCount;
    }

}
