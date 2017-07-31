package com.yn.web;

import com.yn.model.Server;
import com.yn.service.ServerService;
import com.yn.utils.BeanCopy;
import com.yn.vo.ServerVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Xiang on 2017/7/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerControllerTest {

    @Autowired
    ServerService serverService;

    @Test
    public void save() throws Exception {
        ServerVo serverVo = new ServerVo();
        serverVo.setUserId(5L);
        serverVo.setCompanyLogo("http://xxx.jpg");
        serverVo.setCompanyEmail("2710@qq.com");
        serverVo.setCompanyName("JACK Chen发电有限责任公司");
        serverVo.setCompanyAddress("广东省深圳市龙岗区大运软件小镇");
        serverVo.setCompanyAssets(1000000D);
        serverVo.setRegisteredDtm(new Date());
        serverVo.setLegalPerson("成龙");
        serverVo.setLegalPersonPhone("13769394954");
        serverVo.setPersonInCharge("成龙");
        serverVo.setSalesmanPhone("13769394954");
        serverVo.setAptitude("承装（修、试）电力设施许可证五级或以上资质");
        serverVo.setOneYearTurnover(5000000D);
        serverVo.setBankDraft(0);
        serverVo.setCanWithstand(0);
        serverVo.setHadEpc(0);
        serverVo.setMaxNumberOfBuilder(10);
        serverVo.setTolCapacityOfYn(500D);
        serverVo.setPriceaRing(1100D);
        serverVo.setPricebRing(1100D);
        serverVo.setDesignPrice(0D);
        serverVo.setWarrantyYear(5D);
        serverVo.setRank(1);
        serverVo.setFactorage(0.2);
        serverVo.setServerCityIds("199,213");
        serverVo.setServerCityText("深圳市,东莞市");
        serverVo.setType(0);

        Server server = new Server();
        BeanCopy.copyProperties(serverVo, server);
        serverService.save(server);
    }

}