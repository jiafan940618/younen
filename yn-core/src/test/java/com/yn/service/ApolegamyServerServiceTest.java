package com.yn.service;

import com.yn.model.ApolegamyServer;
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
public class ApolegamyServerServiceTest {
    @Autowired
    ApolegamyServerService apolegamyServerService;

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void save() throws Exception {
        ApolegamyServer apolegamyServer = new ApolegamyServer();
        apolegamyServer.setServerId(1L);
        apolegamyServer.setApolegamyId(1L);
        apolegamyServerService.save(apolegamyServer);
    }

    @Test
    public void delete() throws Exception {
        apolegamyServerService.delete(1L);
    }

    @Test
    public void deleteBatch() throws Exception {
    }

    @Test
    public void findOne1() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findAll1() throws Exception {
    }

    @Test
    public void findAll2() throws Exception {
    }

}