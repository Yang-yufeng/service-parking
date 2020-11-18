package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.constants.RequestUrlConstants;
import com.wzwl.parking.constants.TimeConstants;
import com.wzwl.parking.dao.CarRecordMapper;
import com.wzwl.parking.dao.ParkingLotMapper;
import com.wzwl.parking.dao.PassageMapper;
import com.wzwl.parking.model.CarRecord;
import com.wzwl.parking.model.ParkingLot;
import com.wzwl.parking.model.Passage;
import com.wzwl.parking.service.ParkingService;
import com.wzwl.parking.util.DateUtil;
import com.wzwl.parking.util.HttpUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ParkingServiceImpl
 * @Description TODO
 * @Author yangwu
 * @Date 2020/11/3 16:04
 * @Version 1.0
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    @Resource
    private CarRecordMapper carMapper;

    @Resource
    private ParkingLotMapper parkingLotMapper;

    @Resource
    private PassageMapper passageMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String carIn(String companyId, String parkId, String carNo, String cardNo, Integer entryTime, String entryName, Integer cardType,
                        String entryImage, Integer useSpace,Integer entryPassType,String entryPassRemark) {
        ParkingLot parkingLot = parkingLotMapper.getParkingLotById(companyId,parkId);
        if(parkingLot==null){
            ResultEntity result = new ResultEntity(ResultEnum.PARKINGLOT_NOT_EXISTED);
            return  result.toString();
       }
        //同一车辆入场时间唯一
        CarRecord record = carMapper.getCarRecordByEntryTime(carNo,cardNo,entryTime,companyId);
        if(record!=null){
            ResultEntity result = new ResultEntity(ResultEnum.CAR_IN_ALREADY_EXISTED);
            return result.toString();
        }
        record = new CarRecord();
        record.setCompanyId(companyId);
        //保存车辆记录以平台parkId进行保存,而不是第三方id
        record.setParkId(parkingLot.getId());
        record.setCarNo(carNo);
        record.setCardNo(cardNo);
        record.setEntryTime(entryTime);
        record.setEntryName(entryName);
        record.setCardType(cardType);
        record.setEntryImage(entryImage);
        record.setEntryPassType(entryPassType!=null?entryPassType:0);
        record.setExitPassType(0);
        record.setEntryPassRemark(entryPassRemark);
        carMapper.insert(record);
        //更新停车场剩余车位信息
        parkingLot.setUseSpace(useSpace);
        parkingLot.setFreeSpace(parkingLot.getTotalSpace()-useSpace);
        parkingLotMapper.updateById(parkingLot);
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        return  result.toString();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String carOut(String companyId, String parkId, String carNo, String cardNo, Integer entryTime,  Integer exitTime,
                         String exitName, String exitImage, Integer useSpace,Integer exitPassType,String exitPassRemark) {
        ParkingLot parkingLot = parkingLotMapper.getParkingLotById(companyId,parkId);
        if(parkingLot==null){
            ResultEntity result = new ResultEntity(ResultEnum.PARKINGLOT_NOT_EXISTED);
            return  result.toString();
        }
        CarRecord record = carMapper.getCarRecordByEntryTime(carNo,cardNo,entryTime,companyId);
        if(record==null){
            record = new CarRecord();
            record.setCompanyId(companyId);
            record.setParkId(parkingLot.getId());
            record.setCarNo(carNo);
            record.setCardNo(cardNo);
            record.setEntryTime(entryTime);
            record.setExitTime(exitTime);
            record.setExitName(exitName);
            record.setExitImage(exitImage);
            record.setEntryPassType(0);
            record.setExitPassType(exitPassType!=null?exitPassType:0);
            record.setExitPassRemark(exitPassRemark);
            carMapper.insert(record);
        }
        record.setEntryTime(entryTime);
        record.setExitTime(exitTime);
        record.setExitName(exitName);
        record.setExitImage(exitImage);
        record.setEntryPassType(0);
        record.setExitPassType(exitPassType!=null?exitPassType:0);
        record.setExitPassRemark(exitPassRemark);
        carMapper.updateById(record);
        //更新停车场剩余车位信息
///        parkingLot.setUseSpace(useSpace);
///        parkingLot.setFreeSpace(parkingLot.getTotalSpace()-useSpace);
///        parkingLotMapper.updateById(parkingLot);
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        return  result.toString();
    }

    @Override
    public String carCharge(String companyId, String parkId, String carNo, String cardNo, Integer entryTime,
                            Integer payTime, Integer payMoney) {
        ParkingLot parkingLot = parkingLotMapper.getParkingLotById(companyId,parkId);
        if(parkingLot==null){
            ResultEntity result = new ResultEntity(ResultEnum.PARKINGLOT_NOT_EXISTED);
            return  result.toString();
        }
        CarRecord record = carMapper.getCarRecordByEntryTime(carNo,cardNo,entryTime,companyId);
        if(record==null){
            record = new CarRecord();
            record.setCompanyId(companyId);
            record.setParkId(parkingLot.getId());
            record.setEntryTime(entryTime);
            record.setCarNo(carNo);
            record.setCardNo(cardNo);
            record.setPayMoney(payMoney);
            record.setPayTime(payTime);
            record.setExitPassType(0);
            record.setEntryPassType(0);
            carMapper.insert(record);
        }
        record.setPayMoney(payMoney);
        record.setPayTime(payTime);
        carMapper.updateById(record);
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        return  result.toString();
    }

    @Override
    public String addParkingLot(String companyId, String parkId, String name, Integer totalSpace, Integer limitLength,
                                Integer limitWidth, Integer limitHeight, String address, String telephone, Integer workStartTime,
                                Integer workEndTime, String payRule, String lon, String lat, String imgUrl) {
        ParkingLot parkingLot = parkingLotMapper.getParkingLotById(companyId,parkId);
        if(parkingLot!=null){
            ResultEntity result = new ResultEntity(ResultEnum.PARKINGLOT_ALREADY_EXISTED);
            return  result.toString();
        }
        parkingLot = new ParkingLot();
        parkingLot.setCompanyId(companyId);
        parkingLot.setParkId(parkId);
        parkingLot.setName(name);
        parkingLot.setTotalSpace(totalSpace);
        parkingLot.setLimitLength(limitLength);
        parkingLot.setLimitWidth(limitWidth);
        parkingLot.setLimitHeight(limitHeight);
        parkingLot.setAddress(address);
        parkingLot.setTelephone(telephone);
        parkingLot.setWorkStartTime(workStartTime);
        parkingLot.setWorkEndTime(workEndTime);
        parkingLot.setPayRule(payRule);
        parkingLot.setLon(lon);
        parkingLot.setLat(lat);
        parkingLot.setImgUrl(imgUrl);
        parkingLot.setCreateTime((int) (System.currentTimeMillis()/1000));
        parkingLotMapper.insert(parkingLot);
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        return  result.toString();
    }

    @Override
    public String listParkingLot(String companyId, Integer page, Integer pageSize) {
        QueryWrapper<ParkingLot> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("company_id",companyId);
        IPage<ParkingLot> lotPage = new Page<>(page,pageSize);
        lotPage = parkingLotMapper.selectPage(lotPage,queryWrapper);
        List<ParkingLot> list = lotPage.getRecords();
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        //总记录数
        long total = lotPage.getTotal();
        //当前页记录数
        int current = list.size();
        JSONObject json = new JSONObject();
        json.put("total", total);
        if(current>0) {
            json.put("parkingLotList", list);
        }
        result.setData(json);
        return  result.toString();
    }

    @Override
    public String listCarRecord(String companyId, Integer page, Integer pageSize, String carNo, String cardNo, Integer startTime,Integer endTime) {
        QueryWrapper<CarRecord> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("company_id",companyId);
        if(carNo!=null) {
            queryWrapper.like("car_no", carNo);
        }
        if(cardNo!=null) {
            queryWrapper.like("card_no", cardNo);
        }
        if(startTime!=null) {
            queryWrapper.ge("entry_time", startTime);
        }
        if(endTime!=null) {
            queryWrapper.le("entry_time", endTime);
        }
        queryWrapper.orderByDesc("entry_time");
        IPage<CarRecord> recordPage = new Page<>(page,pageSize);
        recordPage = carMapper.selectPage(recordPage,queryWrapper);
        List<CarRecord> list = recordPage.getRecords();
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
        result.setData(json);
        return  result.toString();
    }

    @Override
    public String export(String companyId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        QueryWrapper<CarRecord> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("company_id",companyId);
        IPage<CarRecord> recordPage = new Page<>(1,100);
        recordPage = carMapper.selectPage(recordPage,queryWrapper);
        List<CarRecord> list = recordPage.getRecords();
        //二、 数据转成excel
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String fileName = "消费记录.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("车牌号");
        cell = row.createCell(1);
        cell.setCellValue("卡号");
        cell = row.createCell(2);
        cell.setCellValue("入场时间");
        cell = row.createCell(3);
        cell.setCellValue("出场时间");
        cell = row.createCell(4);
        cell.setCellValue("入口名");
        cell = row.createCell(5);
        cell.setCellValue("卡类型");
        cell = row.createCell(6);
        cell.setCellValue("出口名");
        cell = row.createCell(7);
        cell.setCellValue("支付时间");
        cell = row.createCell(8);
        cell.setCellValue("支付金额");
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            CarRecord record = list.get(i);
            rows = sheet.createRow(i+1);
            cells = rows.createCell(0);
            cells.setCellValue(record.getCarNo()!=null?record.getCarNo():"");
            cells = rows.createCell(1);
            cells.setCellValue(record.getCardNo()!=null?record.getCardNo():"");
            cells = rows.createCell(2);
            cells.setCellValue(record.getEntryTime()!=null?record.getEntryTime():0);
            cells = rows.createCell(3);
            cells.setCellValue(record.getExitTime()!=null?record.getExitTime():0);
            cells = rows.createCell(4);
            cells.setCellValue(record.getEntryName()!=null?record.getEntryName():"");
            cells = rows.createCell(5);
            cells.setCellValue(record.getCardType()!=null?record.getCardType():0);
            cells = rows.createCell(6);
            cells.setCellValue(record.getExitName()!=null?record.getExitName():"");
            cells = rows.createCell(7);
            cells.setCellValue(record.getPayTime()!=null?record.getPayTime():0);
            cells = rows.createCell(8);
            cells.setCellValue(record.getPayMoney()!=null?record.getPayMoney():0);
        }
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        return result.toString();
    }

    @Override
    public String listPassages(String companyId, Integer page, Integer pageSize) {
        {
            QueryWrapper<Passage> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("company_id",companyId);
            IPage<Passage> passagesPage = new Page<>(page,pageSize);
            passagesPage = passageMapper.selectPage(passagesPage,queryWrapper);
            List<Passage> list = passagesPage.getRecords();
            ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
            //总记录数
            long total = passagesPage.getTotal();
            if(total==0){
                //没有查询到记录从科拓Agent获取
                //再将数据上报到上层应用
                Map<String, Object> requestMap=new HashMap<String, Object>();
                //reportMap.put("companyId", companyId);
                requestMap.put("appId", "10156");
                requestMap.put("parkId", 115);
                requestMap.put("serviceCode", "getParkingNode");
                requestMap.put("ts", System.currentTimeMillis()+"");
                requestMap.put("reqId", UUID.randomUUID().toString().replace("-",""));
                requestMap.put("pageIndex", page);
                requestMap.put("pageSize", pageSize);
                String response=HttpUtil.doPostRequest(RequestUrlConstants.GET_PASSAGES_URL, requestMap);
                JSONObject responseJson =JSON.parseObject(response);
                JSONArray passagesArray  = responseJson.getJSONObject("data").getJSONArray("nodeList");
                for(int i=0;i<passagesArray.size();i++){
                    JSONObject json =(JSONObject) passagesArray.get(i);
                    Passage passage = new Passage();
                    passage.setCode(json.getInteger("id"));
                    passage.setCompanyId(companyId);
                    passage.setParkId(115);
                    passage.setIp(json.getString("nodeIp"));
                    passage.setUseType(json.getInteger("useType"));
                    passage.setName(json.getString("nodeName"));
                    passageMapper.insert(passage);
                }

                System.out.println(response);
            }
            //当前页记录数
            int current = list.size();
            JSONObject json = new JSONObject();
            json.put("total", total);
            if(current>0) {
                json.put("passagesList", list);
            }
            result.setData(json);
            return  result.toString();

        }
    }

    @Override
    public String carDataTrend(String companyId,Integer dayTimestamp,Integer type,Integer startTime,Integer endTime) {
        JSONObject returnJson = new JSONObject();
        JSONArray chargeTrendArray=new JSONArray();
        JSONArray carInTrendArray = new JSONArray();
        int points = 0;
        int pointInterval=0;
        if(type==TimeConstants.DAY_TREND_TYPE) {
            //天趋势时，以当前时间（整点）+1为终点，向前推24小时，如现在1点30分，以2点为终点向前推24小时，取点间隔15min
            dayTimestamp=DateUtil.getTodayStartTime(true, false, false) + 3600 - 86400;
            pointInterval=TimeConstants.DAY_TREND_POINTS_INTERVAL;
            points=24 * (3600 / pointInterval);
        }
        if(type==TimeConstants.WEEK_TREND_TYPE){
            //周趋势时，以当前时间（整点）+1为终点，向前推7*24小时，如现在1点30分，以2点为终点向前推7*24小时，取点间隔2h
            dayTimestamp=DateUtil.getTodayStartTime(true, false, false) + 3600 - 86400*7;
            pointInterval=TimeConstants.WEEK_TREND_POINTS_INTERVAL;
            points=7 * (86400 / pointInterval);
        }
        if(type==TimeConstants.MONTH_TREND_TYPE){
            //月趋势时，以当前时间（整点）+1为终点，向前推30*24小时，如现在1点30分，以2点为终点向前推30*24小时，取点间隔6h
            dayTimestamp=DateUtil.getTodayStartTime(true, false, false) + 3600 - 86400*30;
            pointInterval=TimeConstants.MONTH_TREND_POINTS_INTERVAL;
            points=30 * (86400 / pointInterval);
        }
        if(type==TimeConstants.YEAR_TREND_TYPE){
            //年趋势时，以当前时间（整点）+1为终点，向前推360*24小时，如现在1点30分，以2点为终点向前推7*24小时，取点间隔3d
            dayTimestamp=DateUtil.getTodayStartTime(false, false, false) - 86400*360;
            pointInterval=TimeConstants.YEAR_TREND_POINTS_INTERVAL;
            points=360*86400/pointInterval;
        }
        if(type==TimeConstants.DEFINE_TREND_TYPE){
            //自定义选择时间段时，固定返回100个点数据
            points = 100;
            pointInterval = (endTime-startTime)/100;
            dayTimestamp = startTime;
        }
        for (int i=1; i <= points; i++) {
            JSONObject timeChargeJson=new JSONObject();
            JSONObject timeCarInJson=new JSONObject();
            //开始时间
            int computeStartTime = dayTimestamp + pointInterval * (i - 1);
            int computeEndTime = dayTimestamp + pointInterval * i ;
            Integer sumCharge=carMapper.getTimeCarCharge(computeStartTime, computeEndTime,companyId);
            Integer countCarIn  = carMapper.getTimeCarIn(computeStartTime,computeEndTime,companyId);
            timeChargeJson.put(DateUtil.stampToDate(computeEndTime,"yyyy-MM-dd HH:mm"),
                    sumCharge == null ? 0 : sumCharge);
            timeCarInJson.put(DateUtil.stampToDate(computeEndTime,"yyyy-MM-dd HH:mm"),
                    countCarIn == null ? 0 : countCarIn);
            chargeTrendArray.add(timeChargeJson);
            carInTrendArray.add(timeCarInJson);
        }
        ResultEntity result = new ResultEntity(ResultEnum.SUCCESS);
        returnJson.put("chargeTrend", chargeTrendArray);
        returnJson.put("carInTrend", carInTrendArray);
        returnJson.put("points", points);
        result.setData(returnJson);
        return result.toString();
    }


}
