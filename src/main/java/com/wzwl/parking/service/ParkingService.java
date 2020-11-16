package com.wzwl.parking.service;


import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName ParkingService
 * @Description TODO
 * @Author yangwu
 * @Date 2020/11/3 16:01
 * @Version 1.0
 */
public interface ParkingService  {
    /**
     * 车辆进场
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
    String carIn( String companyId, String parkId, String carNo, String cardNo, Integer entryTime, String entryName,
                  Integer cardType, String entryImage,Integer useSpace,Integer entryPassType,String entryPassRemark);

    /**
     * 车辆出场
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
    String carOut( String companyId, String parkId,String carNo, String cardNo, Integer entryTime, Integer exitTime,
                   String exitName, String exitImage,Integer useSpace,Integer exitPassType,String exitPassRemark);

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
    String carCharge (String companyId, String parkId, String carNo, String cardNo, Integer entryTime, Integer payTime,
                      Integer payMoney);

    /**
     * 添加停车场
     * @param companyId 企业ID
     * @param parkId 停车场ID
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
    String addParkingLot(String companyId, String parkId, String name, Integer totalSpace, Integer limitLength, Integer limitWidth,
                         Integer limitHeight, String address, String telephone, Integer workStartTime, Integer workEndTime,
                         String payRule, String lon, String lat, String imgUrl);


    /**
     * 查询停车场列表
     * @param companyId 企业ID
     * @param page 分页数
     * @param pageSize 每页返回最大记录数
     * @return
     */
    String listParkingLot(String companyId, Integer page, Integer pageSize);



    /**
     * 查询车辆出/入/缴费记录
     * @param companyId 企业ID
     * @param page 查询页
     * @param pageSize 每页显示数据
     * @param carNo 车牌号
     * @param cardNo 卡号
     * @param exitTime 出场时间
     * @return
     */
    String listCarRecord(String companyId, Integer page, Integer pageSize,String carNo,String cardNo,Integer exitTime);

    /**
     * 导出
     * @param companyId 企业ID
     * @return
     */
    String export(String companyId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;


    /**
     * 查询设备列表
     * @param companyId 企业ID
     * @param page 分页数
     * @param pageSize 每页返回最大记录数
     * @return
     */
    String listPassages( String companyId, Integer page,Integer pageSize);

    /**
     * 车辆数据趋势
     * @param companyId 企业ID
     * @param dayTimestamp 时间戳
     * @param type 类型（1：过去24小时，2：过去1周，3：过去30天，4：过去一年，5：自定义选择时间
     * @param startTime 自定义开始时间
     * @param endTime 自定义结束时间
     * @return
     */
    String carDataTrend(String companyId,Integer dayTimestamp,Integer type,Integer startTime,Integer endTime);



}
