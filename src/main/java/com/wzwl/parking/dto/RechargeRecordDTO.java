package com.wzwl.parking.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName RechargeRecordDTO
 * @Description 充值记录
 * @Author huff
 * @Date 2020/11/13 13:43
 * @Version 1.0
 */
@Data
public class RechargeRecordDTO {

    /**
     * 车场id
     */
    @NotNull(message="parkId不能为空")
    private Integer parkId;


    /**
     * 公司ID
     */
    @NotEmpty(message = "companyId不能为空")
    private String companyId;

    /**
     * 卡片ID
     */
    @NotNull(message = "cardId不能为空")
    private Integer cardId;

    /**
     * 订单号
     */
    @NotEmpty(message = "orderNo不能为空")
    private String orderNo;

    /**
     * 固定车类型
     */
    @NotNull(message = "carType不能为空")
    private Integer carType;

    /**
     * 支付渠道
     */
    @NotNull(message = "payChannel不能为空")
    private Integer payChannel;

    /**
     * 充值方式,(0 钱(单位:分),1 月,2 天,3 小时,4次)
     */
    @NotNull(message = "chargeMethod不能为空")
    private Integer chargeMethod;

    /**
     * 充值车的充值月数或天数或次数或钱
     */
    @NotNull(message = "chargeNumber不能为空")
    private Integer chargeNumber;

    /**
     * 支付金额
     */
    @NotNull(message = "amount不能为空")
    private Integer amount;

    /**
     * 免费赠送的月数或天数或次数
     */
    @NotNull(message = "freeNumber不能为空")
    private Integer freeNumber;

    /**
     * 充值开始时间建议00:00:00(yyyy-MM-dd HH:mm:ss）
     */
    @NotEmpty(message = "validFrom不能为空")
    private String validFrom;

    /**
     * 充值结束时间建议23:59:59(yyyy-MM-dd HH:mm:ss）
     */
    @NotEmpty(message = "validTo不能为空")
    private String validTo;

    /**
     * 创建时间(格式：yyyy-MM-dd HH:mm:ss)
     */
    @NotEmpty(message = "创建时间不能为空")
    private String createTime;

    /**
     * 备注
     */
    @NotEmpty(message = "remark不能为空")
    private String remark;

    /**
     * 充值类型(0:充值,1:退款)
     */
    @NotNull(message = "rechargeType不能为空")
    private Integer rechargeType;

    /**
     * 操作标识 0=删除 1=新增 2=更新
     */
    @NotNull(message = "operationType不能为空")
    private Integer operationType;

    /**
     * 操作人员
     */
    @NotEmpty(message = "operator不能为空")
    private String operator;

    /**
     * 支付来源
     */
    @NotEmpty(message = "paySource不能为空")
    private String paySource;

}
