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
        JSONObject json = new JSONObject();
        json.put("companyId","tx");
        JSONObject contentJson = new JSONObject();
        contentJson.put("name","yangwu");
        contentJson.put("sex","1");
        contentJson.put("age",30);
        json.put("content",contentJson);
        carDataSender.sendTest(json.toJSONString());
    }

    @Test
    public void testCarDataTrend(){
        String result = parkingService.carDataTrend("zz",1604937600);
        System.out.println(result);
    }

}
