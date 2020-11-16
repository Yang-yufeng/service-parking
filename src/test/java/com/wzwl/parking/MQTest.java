package com.wzwl.parking;

import com.alibaba.fastjson.JSONObject;
import com.wzwl.parking.mq.CarDataSender;
import com.wzwl.parking.service.ParkingService;
import com.wzwl.parking.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName MQTest
 * @Description TODO
 * @Author yangwu
 * @Date 2020/11/14 14:58
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQTest {

    @Resource
    private ParkingService parkingService;

    @Resource
    private CarDataSender carDataSender;

    @Test
    public void testCarDataSend() throws Exception {
        JSONObject json=new JSONObject();
        json.put("companyId", "tx");
        JSONObject contentJson=new JSONObject();
        contentJson.put("name", "yangwu");
        contentJson.put("sex", "1");
        contentJson.put("age", 30);
        json.put("content", contentJson);
        carDataSender.sendTest(json.toJSONString());
    }

    @Test
    public void testCarDataTrend() {
        String result1 = parkingService.carDataTrend("zz",1604937600,1,0,0);
        System.out.println(result1);
        String result2 = parkingService.carDataTrend("zz",1604937600,2,0,0);
        System.out.println(result2);
        String result3=parkingService.carDataTrend("zz", 1604937600, 3, 0, 0);
        System.out.println(result3);
        String result4=parkingService.carDataTrend("zz", 1604937600, 4, 0, 0);
        System.out.println(result4);
        String result5=parkingService.carDataTrend("zz", 1604937600, 5, 1605283200, 1605456000);
        System.out.println(result5);
    }

    @Test
    public void testTime() {
        //System.out.println(DateUtil.getTodayStartTime(true,false,false));
        System.out.println(DateUtil.stampToDate(1605424500, "yyyy-MM-dd HH:mm"));
    }

}
