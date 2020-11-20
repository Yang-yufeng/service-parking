package com.wzwl.parking.vo;

import lombok.Data;

/**
 * @ClassName OverViewVO
 * @Description TODO
 * @Author huff
 * @Date 2020/11/19 17:51
 * @Version 1.0
 */
@Data
public class OverViewVO {

    private RechargeVo rechargeVo;

    private CarEntryAndExitVO carEntryAndExitVO;

    private EnvironmentVO environmentVO;

}
