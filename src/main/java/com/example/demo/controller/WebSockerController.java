package com.example.demo.controller;

import com.example.demo.domain.WiselyMessageBean;
import com.example.demo.domain.WiselyResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSockerController {


    @MessageMapping("/welcome")//浏览器想服务器发送数据时，映射/welcome这个地址，类似于requestMapping
    @SendTo("/topic/getResponse")//当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public WiselyResponseBean say(WiselyMessageBean message) throws Exception {
        Thread.sleep(3000);
        return new WiselyResponseBean("Welcome, " + message.getName() + "!");
    }
}