package com.wzwl.parking.controller;

import com.wzwl.parking.service.ParkingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName ParkingController
 * @Description 停车场控制器
 * @Author yangwu
 * @Date 2020/11/3 14:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1")
public class ParkingController {

    @Resource
    private ParkingService parkingService;

    /**
     * 车辆进场记录
     * @param companyId 企业ID
     * @param parkId 停车场ID
     * @param carNo 车牌号
     * @param cardNo 入场卡号
     * @param entryTime 进场时间
     * @param entryName 入口名
     * @param cardType 卡类型
     * @param entryImage 入场图片
     * @param useSpace 已用车位
     * @return
     */
    @RequestMapping("/carIn")
    public String carIn(@RequestParam String companyId, @RequestParam String parkId,
                        String carNo,String cardNo, @RequestParam Integer entryTime,
                        @RequestParam String entryName, @RequestParam Integer cardType,
                        String entryImage,@RequestParam Integer useSpace,Integer entryPassType,String entryPassRemark){
        return parkingService.carIn(companyId,parkId,carNo,cardNo,entryTime,entryName,cardType,
                entryImage,useSpace,entryPassType,entryPassRemark);
    }

    /**
     *
     * @param companyId 企业ID
     * @param parkId 停车场ID
     * @param carNo 车牌号
     * @param cardNo 入场卡号
     * @param entryTime 入场时间
     * @param exitTime 出场时间
     * @param exitName 出口名
     * @param exitImage 出场照片
     * @param useSpace 已用车位
     * @return
     */
    @RequestMapping("/carOut")
    public String carOut(@RequestParam String companyId, @RequestParam String parkId,
                         @RequestParam Integer entryTime, @RequestParam Integer exitTime,
                         @RequestParam String exitName, String exitImage,String carNo, String cardNo,
                         @RequestParam Integer useSpace,Integer exitPassType,String exitPassRemark){
        return parkingService.carOut(companyId,parkId,carNo,cardNo,entryTime,exitTime,exitName,exitImage,useSpace,exitPassType,
                exitPassRemark);
    }

    /**
     * 车辆缴费记录
     * @param companyId  企业ID
     * @param parkId 停车场ID
     * @param carNo 车牌号
     * @param cardNo 卡号
     * @param entryTime 入场时间
     * @param payTime 支付时间
     * @param payMoney 支付金额
     * @return
     */
    @RequestMapping("/carCharge")
    public String carCharge(@RequestParam String companyId, @RequestParam String parkId,
                         @RequestParam Integer entryTime, @RequestParam Integer payTime,
                         @RequestParam Integer payMoney,String carNo, String cardNo){
        return parkingService.carCharge(companyId,parkId,carNo,cardNo,entryTime,payTime,payMoney);
    }


    /**
     * 添加停车场
     * @param companyId 企业ID
     * @param parkId 三方停车场ID
     * @param name 停车场名
     * @param totalSpace 总容量
     * @param limitLength 限长
     * @param limitWidth 限宽
     * @param limitHeight 限高
     * @param address 详细地址
     * @param telephone 联系方式
     * @param workStartTime 营业开始时间
     * @param workEndTime 营业结束时间
     * @param payRule 计费规则
     * @param lon 经度
     * @param lat 纬度
     * @param imgUrl 图片
     * @return
     */
    @RequestMapping("/addParkinglot")
    public String addParkingLot(@RequestParam String companyId, @RequestParam String parkId, @RequestParam String name,
                                @RequestParam Integer totalSpace, @RequestParam Integer limitLength, @RequestParam Integer limitWidth,
                                @RequestParam Integer limitHeight, @RequestParam String address, @RequestParam String telephone
                                , @RequestParam Integer workStartTime, @RequestParam Integer workEndTime,
                                String payRule, String lon, String lat, String imgUrl){
        return parkingService.addParkingLot(companyId,parkId,name,totalSpace,limitLength,limitWidth,limitHeight,address,telephone,
                workStartTime,workEndTime,payRule,lon,lat,imgUrl);
    }

    /**
     * 查询停车场列表
     * @param companyId 企业ID
     * @param page 分页数
     * @param pageSize 每页返回最大记录数
     * @return
     */
    @RequestMapping("/listParkinglots")
    public String listParkingLot(@RequestParam String companyId,@RequestParam(defaultValue="1") Integer page,
                                 @RequestParam(defaultValue="10") Integer pageSize){
        return parkingService.listParkingLot(companyId,page,pageSize);
    }

    /**
     * 查询车辆出/入/缴费记录
     * @param companyId 企业ID
     * @param page 查询页
     * @param pageSize 每页显示数据
     * @param carNo 车牌号
     * @param cardNo 卡号
     * @param startTime 开始时间（入场时间）
     * @param endTime 结束时间（入场时间）
     * @return
     */
    @RequestMapping("/listCarRecords")
    public String listCarRecord(@RequestParam String companyId,@RequestParam(defaultValue="1") Integer page,
                                 @RequestParam(defaultValue="10") Integer pageSize,String carNo,String cardNo,
                                Integer startTime,Integer endTime){
        return parkingService.listCarRecord(companyId,page,pageSize,carNo,cardNo,startTime,endTime);
    }

    /**
     * 查询停车场通道信息
     * @param companyId 企业ID
     * @param page 分页数
     * @param pageSize 每页返回最大记录数
     * @return
     */
    @RequestMapping("/listPassages")
    public String listPassages(@RequestParam String companyId,@RequestParam(defaultValue="1") Integer page,
                               @RequestParam(defaultValue="10") Integer pageSize){
        return parkingService.listPassages(companyId,page,pageSize);
    }

    @RequestMapping("/export")
    public String export(String companyId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return parkingService.export(companyId,request,response);
    }

    /**
     * 车辆数据趋势统计
     * @param companyId
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/statistics")
    public String carStatistics(@RequestParam String companyId,@RequestParam Integer type,Integer startTime,Integer endTime){
        return parkingService.carDataTrend(companyId,null,type,startTime,endTime);
    }








}
