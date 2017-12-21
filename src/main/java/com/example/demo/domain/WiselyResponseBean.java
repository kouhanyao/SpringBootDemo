package com.example.demo.domain;

/**
 * Created by 寇含尧 on 2017/10/24.
 */
public class WiselyResponseBean {
    private String responseMessage;
    public WiselyResponseBean(String responseMessage){
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage(){
        return responseMessage;
    }
}
