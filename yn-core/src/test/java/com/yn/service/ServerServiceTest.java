package com.yn.service;

import com.yn.model.Server;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Xiang on 2017/7/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerServiceTest {
    @Autowired
    ServerService serverService;

    @Test
    public void save() throws Exception {
        Server server = serverService.findOne(1L);
        server.setType(1);
        serverService.save(server);
    }

}