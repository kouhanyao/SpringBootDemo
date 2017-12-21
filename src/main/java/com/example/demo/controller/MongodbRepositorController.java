package com.example.demo.controller;

import com.example.demo.domain.LocationBean;
import com.example.demo.domain.StudentBean;
import com.example.demo.service.DomainMongoRepository.StudentRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
@RestController
@RequestMapping("/mongodb")
public class MongodbRepositorController {
    @Autowired
    StudentRepositoryService studentRepositoryService;

    @RequestMapping("/save")
    public StudentBean save(){
        StudentBean studentBean = new StudentBean("khy",24);
        Collection<LocationBean> locations =  new LinkedHashSet<>();
        LocationBean loc1 = new LocationBean("上海","2009");
        LocationBean loc2 = new LocationBean("合肥","2010");
        LocationBean loc3 = new LocationBean("广州","2011");
        LocationBean loc4 = new LocationBean("马鞍山","2012");
        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);
        studentBean.setLocations(locations);
        //mongodb保存数据不填入id时，id会自动生成
        return studentRepositoryService.save(studentBean);
    }

    @RequestMapping("/findByName")
    public StudentBean findByName(String name){
        return studentRepositoryService.findByName(name);

    }

    @RequestMapping("/withQueryFindByAge")
    public List<StudentBean> withQueryFindByAge(Integer age){
        return studentRepositoryService.withQueryFindByAge(age);
    }
}
