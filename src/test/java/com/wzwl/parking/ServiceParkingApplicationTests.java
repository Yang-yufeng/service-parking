package com.wzwl.parking;


import com.wzwl.parking.service.ConfigService;
import com.wzwl.parking.service.ParkingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceParkingApplicationTests {

    @Resource
    public ParkingService parkingService;

    @Resource
    public ConfigService configService;



    @Test
    public void testCarIn(){
       String result =  parkingService.carIn("zz","ketuoId1","粤MJ9176",null,
               10086, "入口1",1,null,100,null,null);
       System.out.println(result);
    }

    @Test
    public void testCarOut(){
        String result =  parkingService.carOut("zz","ketuoId1","粤MJ9176",null,
                10086,100020,"出口1",null,99,null,null);
        System.out.println(result);
    }

    @Test
    public void testCarCharge(){
        String result =  parkingService.carCharge("zz","ketuoId1","粤MJ9176",null
                ,10086,10090,1000);
        System.out.println(result);
    }


    @Test
    public void testAddParkingLot(){
        String result =  parkingService.addParkingLot("zz","park0015","停车场A",3000,10,
                3,5,"科兴科学园A3栋1楼","075588888888",0,23,"每小时10元"
        ,"110.222","29.002",null);
        System.out.println(result);
    }

    @Test
    public void testParkingLostList(){
        String result =  parkingService.listParkingLot("zz",1,3);
        System.out.println(result);
    }

    @Test
    public void testCarRecordList(){
        String result =  parkingService.listCarRecord("zz",1,3,"粤M",null,1,1800000000);
        System.out.println(result);
    }

    @Test
    public void testGetCompanyId(){
        String result = configService.getCompanyConfig("appIdzz");
        System.out.println(result);
    }



}
