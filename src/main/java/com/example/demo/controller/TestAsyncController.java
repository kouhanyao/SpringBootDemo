package com.example.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by 寇含尧 on 2017/7/14.
 */
@Component
public class TestAsyncController {
    @Async
    public Future<String> sayHello1() throws InterruptedException {
        int thinking = 4;
        Thread.sleep(thinking * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!");
        return new AsyncResult<String>("发送消息用了"+thinking+"秒");
    }

    @Async
    public void sayHello3() throws InterruptedException {
        Thread.sleep(4 * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!");
    }

    @Async
    public String sayHello2() throws InterruptedException {
        Thread.sleep(2 * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!");
        return "我爱你啊!";// 调用方调用后会立即返回,所以返回null
    }
}
