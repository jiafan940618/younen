package com.yn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Xiang on 2017/7/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QualificationsServerServiceTest {

    @Autowired
    QualificationsServerService qualificationsServerService;

    @Test
    public void save() throws Exception {
        qualificationsServerService.saveBatch("1",1L);
    }

}