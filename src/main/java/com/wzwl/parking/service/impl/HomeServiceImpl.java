package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.CarRecordMapper;
import com.wzwl.parking.dao.ParkingLotMapper;
import com.wzwl.parking.dao.RechargeMapper;
import com.wzwl.parking.service.HomeService;
import com.wzwl.parking.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
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
    private ParkingLotMapper parkingLotMapper;

    @Resource
    private CarRecordMapper carRecordMapper;

    @Resource
    private RechargeMapper rechargeMapper;

    /**
     * 获取首页运营金额、出入统计、开闸统计的数据
     * @param companyId
     * @param parkId
     * @return
     */
    @Override
    public String getData(String companyId, String parkId) {

        JSONObject resultData = new JSONObject();

        //计算出今日凌晨、昨日凌晨、明日凌晨的时间戳
        Calendar now = Calendar.getInstance();
        long today = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        now.add(Calendar.DATE,-1);
        long yesterday = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        now.add(Calendar.DATE,2);
        long tomorrow = DateUtil.getEarlyMorning(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

        //获取月租车充值记录（昨日及今日）
        JSONObject recharge = new JSONObject();
        int yesterdayMonthlyFee = rechargeMapper.getMonthlyFee(companyId,parkId,yesterday,today);
        int todayMonthlyFee = rechargeMapper.getMonthlyFee(companyId,parkId,today,tomorrow);

        //获取今日缴费记录（昨日及今日）
        int yesterdayFee = rechargeMapper.getDailyFee(companyId,parkId,yesterday,today);
        int todayFee = rechargeMapper.getDailyFee(companyId,parkId,today,tomorrow);
        //获得今日临停费用上升比率
        double todayFeeRate;
        if ((yesterdayMonthlyFee+yesterdayFee)==0){
            todayFeeRate = 100.00;
        }else {
            todayFeeRate = new BigDecimal((float)(todayMonthlyFee+todayFee-yesterdayMonthlyFee-yesterdayFee)/(yesterdayMonthlyFee+yesterdayFee))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        //获得今日月租车费用上升比率
        double todayMonthlyFeeRate;
        if (yesterdayMonthlyFee==0){
            todayMonthlyFeeRate = 100.00;
        }else {
            todayMonthlyFeeRate = new BigDecimal((float)(todayMonthlyFee-yesterdayMonthlyFee)/(yesterdayMonthlyFee))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        recharge.put("todayFee",todayMonthlyFee+todayFee);
        recharge.put("todayMonthlyFee",todayMonthlyFee);
        recharge.put("todayFeeRate",todayFeeRate);
        recharge.put("todayMonthlyFeeRate",todayMonthlyFeeRate);
        resultData.put("recharge",recharge);

        //车辆出入统计
        JSONObject carEntryAndExitCount = new JSONObject();
        //获取进场上报记录（昨日及今日）
        int yesterdayEntryCount = carRecordMapper.getEntryCountByTime(companyId,parkId,yesterday,today);
        int todayEntryCount = carRecordMapper.getEntryCountByTime(companyId,parkId,today,tomorrow);
        //获取今日较昨日入车数量百分比
        double todayEntryRate;
        if (yesterdayEntryCount==0){
            todayEntryRate = 100.00;
        }else {
            todayEntryRate = new BigDecimal((float)(todayEntryCount-yesterdayEntryCount)/yesterdayEntryCount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayEntryCount",todayEntryCount);
        carEntryAndExitCount.put("todayEntryRate",todayEntryRate);
        //获取出场上报记录（昨日及今日）
        int yesterdayExitCount = carRecordMapper.getExitCountByTime(companyId,parkId,yesterday,today);
        int todayExitCount = carRecordMapper.getExitCountByTime(companyId,parkId,today,tomorrow);
        //获取今日较昨日入车数量百分比
        double todayExitRate;
        if (yesterdayExitCount==0){
            todayExitRate = 100.00;
        }else {
            todayExitRate = new BigDecimal((float)(todayExitCount-yesterdayExitCount)/yesterdayExitCount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayExitCount",todayExitCount);
        carEntryAndExitCount.put("todayExitRate",todayExitRate);
        //获取车位饱和率
        int yesterdayHoldCount = parkingLotMapper.getParkingSpaceCount(companyId,parkId,yesterday,today);
        int todayHoldCount = parkingLotMapper.getParkingSpaceCount(companyId,parkId,today,yesterday);
        int parkingSpaceNum = parkingLotMapper.getParkingSpaceCount(companyId,parkId,today,yesterday);
        double todayParkingSpaceRate = 0;
        double todayThanYesterdayRate = 0;
        if (parkingSpaceNum==0){
            todayParkingSpaceRate = 100.00;
            todayThanYesterdayRate = 100.00;
        }else {
            todayParkingSpaceRate = new BigDecimal((float)todayHoldCount/parkingSpaceNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            todayThanYesterdayRate = new BigDecimal((float)(todayHoldCount-yesterdayHoldCount)/parkingSpaceNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        carEntryAndExitCount.put("todayParkingSpaceRate",todayParkingSpaceRate);
        carEntryAndExitCount.put("todayThanYesterdayRate",todayThanYesterdayRate);
        resultData.put("carEntryAndExitCount",carEntryAndExitCount);

        //开闸统计
        JSONObject openGateCount = new JSONObject();
        Map<String,Integer> todayExitTypeCount = carRecordMapper.getExitTypeCount(companyId,parkId,today,tomorrow);
        Map<String,Integer> todayEntryTypeCount = carRecordMapper.getEntryTypeCount(companyId,parkId,today,tomorrow);

        int normalTypeCount = 0;
        int unNormalTypeCount = 0;
        int freeTypeCount = 0;
        int passTypeCountNum = 0;
        if (todayExitTypeCount!=null){
            normalTypeCount += todayExitTypeCount.get("0")==null?0:todayExitTypeCount.get("0");
            unNormalTypeCount += todayExitTypeCount.get("2")==null?0:todayExitTypeCount.get("2");
            freeTypeCount += todayExitTypeCount.get("1")==null?0:todayExitTypeCount.get("1");
        }
        if (todayEntryTypeCount!=null){
            normalTypeCount += todayEntryTypeCount.get("0")==null?0:todayEntryTypeCount.get("0");
            unNormalTypeCount += todayEntryTypeCount.get("2")==null?0:todayEntryTypeCount.get("2");
            freeTypeCount += todayEntryTypeCount.get("1")==null?0:todayEntryTypeCount.get("1");
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
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            unNormalTypeCountRate = new BigDecimal((float)unNormalTypeCount/passTypeCountNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            freeTypeCountRate = new BigDecimal((float)freeTypeCount/passTypeCountNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        openGateCount.put("normalTypeCountRate",normalTypeCountRate);
        openGateCount.put("unNormalTypeCountRate",unNormalTypeCountRate);
        openGateCount.put("freeTypeCountRate",freeTypeCountRate);
        resultData.put("openGateCount",openGateCount);

        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        result.setData(resultData);

        return result.toString();
    }

}
