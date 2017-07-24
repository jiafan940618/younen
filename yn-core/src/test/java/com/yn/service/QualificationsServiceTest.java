package com.yn.service;

import com.yn.model.Apolegamy;
import com.yn.model.Qualifications;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Xiang on 2017/7/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QualificationsServiceTest {

    @Autowired
    QualificationsService qualificationsService;

    @Test
    public void save() throws Exception {
        Apolegamy apolegamy = new Apolegamy();
        apolegamy.setApolegamyName("每年检查两次");
        apolegamy.setIconUrl("http://xxx.jpg");
        apolegamy.setImgUrl("http://xxx.jpg");
        apolegamy.setPrice(1000D);
        apolegamy.setUnit("年");

        Set<Apolegamy> apolegamySet = new HashSet<>();
        apolegamySet.add(apolegamy);

        Qualifications qualifications = new Qualifications();
        qualifications.setImgUrl("http://xxx.jpg");
        qualifications.setText("检查");
        qualifications.setApolegamy(apolegamySet);
        qualificationsService.save(qualifications);
    }

    @Test
    public void delete() throws Exception {
        qualificationsService.delete(3L);
    }

}