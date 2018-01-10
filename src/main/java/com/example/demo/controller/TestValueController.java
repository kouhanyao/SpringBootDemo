package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by 寇含尧 on 2017/7/17.
 */
@Service
@PropertySource(value = "classpath:test.properties", encoding = "utf-8")
public class TestValueController {

    @Value("${gg.name}")
    private String ggname;

    @Value("#{settings['book.name']}")
    private String name;

    public String getGgname() {
        return ggname;
    }

    @Autowired
    private Environment environment;

    public String getValue(){
        return name;
    }

}
