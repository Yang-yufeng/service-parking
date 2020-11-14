package com.wzwl.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzwl.parking.common.ResultEntity;
import com.wzwl.parking.common.ResultEnum;
import com.wzwl.parking.dao.CarRecordMapper;
import com.wzwl.parking.dao.ParkingLotMapper;
import com.wzwl.parking.model.CarRecord;
import com.wzwl.parking.model.ParkingLot;
import com.wzwl.parking.service.ParkingService;
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
import java.util.List;

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

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String carIn(String companyId, String parkId, String carNo, String cardNo, Integer entryTime,
                        String entryName, Integer cardType, String entryImage, Integer useSpace) {
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
    public String carOut(String companyId, String parkId, String carNo, String cardNo, Integer entryTime,
                         Integer exitTime, String exitName, String exitImage, Integer useSpace) {
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
            record.setExitTime(exitTime);
            record.setExitName(exitName);
            record.setExitImage(exitImage);
            carMapper.insert(record);
        }
        record.setExitTime(exitTime);
        record.setExitName(exitName);
        record.setExitImage(exitImage);
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
    public String listCarRecord(String companyId, Integer page, Integer pageSize, String carNo, String cardNo, Integer exitTime) {
        QueryWrapper<CarRecord> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("company_id",companyId);
        if(carNo!=null) {
            queryWrapper.like("car_no", carNo);
        }
        if(cardNo!=null) {
            queryWrapper.like("card_no", cardNo);
        }
        if(exitTime!=null) {
            queryWrapper.ge("exit_time", exitTime);
        }
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
        return null;
    }


}
