package com.example.demo.controller;

import com.example.demo.domain.Msg;
import org.springframework.web.bind.annotation.*;

@RestController
public class CrossOriginController {
    @CrossOrigin
    //@CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/testOne")
    public String studentTest(@RequestBody Msg msg){

        return "CrossOrigin";
    }

    //@CrossOrigin
    //@CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/testTwo")
    public String testTwo(@ModelAttribute String test){


        return "kkkksuccesskkkk";
    }


    public String test(@ModelAttribute Msg msg){
        return "CrossOrigin";
    }
}
