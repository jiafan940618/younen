package com.yn.service;

import com.yn.model.ApolegamyServer;
import com.yn.model.Server;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Xiang on 2017/7/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerServiceTest {

    @Autowired
    ServerService serverService;

    @Test
    public void save() throws Exception {
        Server server = serverService.findOne(1L);

        ApolegamyServer apolegamyServer = new ApolegamyServer();
        apolegamyServer.setId(14L);
        apolegamyServer.setApolegamyId(10L);
        apolegamyServer.setServerId(1L);

        ApolegamyServer apolegamyServer1 = new ApolegamyServer();
        apolegamyServer1.setId(15L);
        apolegamyServer1.setApolegamyId(11L);
        apolegamyServer1.setServerId(1L);

        Set<ApolegamyServer> apolegamyServers = new HashSet<>();
        apolegamyServers.add(apolegamyServer);
        apolegamyServers.add(apolegamyServer1);

        server.setApolegamyServer(apolegamyServers);

        serverService.save(server);

    }

}