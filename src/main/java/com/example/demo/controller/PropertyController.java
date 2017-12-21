package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 寇含尧 on 2017/10/17.
 */
@RestController
@RequestMapping(value = "/property")
//@PropertySource(value = {"classpath:application.properties"})
public class PropertyController {
    @Value("${book.author}")
    private String bookAuthor;
    @Value("${book.name}")
    private String bookName;

    @RequestMapping(value = "/")
    public String index(){
        return "author:"+bookAuthor+";name="+bookName;
    }
}
