package com.wzwl.parking.dto;

import lombok.Data;

/**
 * @ClassName RechargeRecordDTO
 * @Description 充值记录
 * @Author huff
 * @Date 2020/11/13 13:43
 * @Version 1.0
 */
@Data
public class RechargeRecordDTO extends BaseDTO{

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 停车场ID
     */
    private String parkId;

    /**
     * 卡片ID
     */
    private String cardId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 固定车类型
     */
    private Integer carType;

    /**
     * 支付渠道
     */
    private Integer payChannel;

    /**
     * 充值方式,(0 钱(单位:分),1 月,2 天,3 小时,4次)
     */
    private Integer chargeMethod;

    /**
     * 充值车的充值月数或天数或次数或钱
     */
    private Integer chargeNumber;

    /**
     * 支付金额
     */
    private Integer amount;

    /**
     * 免费赠送的月数或天数或次数
     */
    private Integer freeNumber;

    /**
     * 充值开始时间建议00:00:00(yyyy-MM-dd HH:mm:ss）
     */
    private String validFrom;

    /**
     * 充值结束时间建议23:59:59(yyyy-MM-dd HH:mm:ss）
     */
    private String validTo;

    /**
     * 创建时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 充值类型(0:充值,1:退款)
     */
    private Integer rechargeType;

    /**
     * 操作标识 0=删除 1=新增 2=更新
     */
    private Integer operationType;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 支付来源
     */
    private String paySource;

}
