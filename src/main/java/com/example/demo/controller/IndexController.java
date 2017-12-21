package com.example.demo.controller;

import com.example.demo.configBean.AuthorBeanConfig;
import com.example.demo.domain.PersonBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 寇含尧 on 2017/10/23.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthorBeanConfig authorBeanConfig;
    @RequestMapping(value = "/one")
    public String indexone(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        return "author:"+authorBeanConfig.getName()+";age="+authorBeanConfig.getAge();
    }

    @RequestMapping(value = "/two")
    public String indexTwo(Model model){
        PersonBean single = new PersonBean("aa",11);

        List<PersonBean> people = new ArrayList<PersonBean>();
        PersonBean p1 = new PersonBean("xx",11);
        PersonBean p2 = new PersonBean("yy",22);
        PersonBean p3 = new PersonBean("zz",33);
        people.add(p1);
        people.add(p2);
        people.add(p3);

        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return "index";
    }

    @RequestMapping(value = "")
    public String indexThree(Model model){
        return "login/login";
    }
}
