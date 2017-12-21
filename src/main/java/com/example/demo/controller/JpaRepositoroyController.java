package com.example.demo.controller;

import com.example.demo.domain.PersonBean;
import com.example.demo.service.DomainJpaRepository.PersonRepositoryService;
import com.example.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 寇含尧 on 2017/11/3.
 */
@RestController
@RequestMapping("/jpa")
public class JpaRepositoroyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //1 Spring Data JPA已自动为你注册bean，所以可自动注入
    @Autowired
    PersonRepositoryService personRepositoryService;

    @Autowired
    PersonService personService;

    /*@RequestMapping("/save")
    public PersonBean save(String name, String address, Integer age){
        PersonBean p = personRepositoryService.save(new PersonBean(null,name, age, address));
        return p;
    }*/

    @RequestMapping("/delete")
    public String delete(Long id){
        if(id == null){
            return "id is null";
        }
        personRepositoryService.delete(id);
        return "success";
    }

    @RequestMapping("/findByName")
    public List<PersonBean> findByName(String name){
        List<PersonBean> p = personRepositoryService.findByName(name, new Sort(Sort.Direction.DESC, "age"));
        return p;
    }

    @RequestMapping("/findByNameAndAddress")
    public List<PersonBean> findByNameAndAddress(String name, String address){
        List<PersonBean> p = personRepositoryService.findByNameAndAddress(name, address);
        return p;
    }

    @RequestMapping("/withNameAndAddressQuery")
    public List<PersonBean> withNameAndAddressQuery(String name, String address){
        List<PersonBean> p = personRepositoryService.withNameAndAddressQuery(name, address);
        return p;
    }

    @RequestMapping("/withNameAndAddressNamedQuery")
    public List<PersonBean> withNameAndAddressNamedQuery(String name, String address){
        List<PersonBean> p = personRepositoryService.withNameAndAddressNamedQuery(name, address);
        return p;
    }

    //排序
    @RequestMapping("/sort")
    public List<PersonBean> sort(){
        List<PersonBean> p = personRepositoryService.findAll(new Sort(Sort.Direction.DESC, "age"));
        return p;
    }

    //分页
    @RequestMapping("/page")
    public Page<PersonBean> page(){
        //page从0开始
        Page<PersonBean> p = personRepositoryService.findAll(new PageRequest(0,10, Sort.Direction.DESC, "age", "name"));
        return p;
    }

    /**
     * 使用自定义查询
     * @param person
     * @return
     */
    @RequestMapping("/auto")
    public Page<PersonBean> auto(PersonBean person){
        Page<PersonBean> pagePeople = personRepositoryService.findByAuto(person, new PageRequest(0,10, Sort.Direction.DESC,"age"));
        return pagePeople;

    }

    //缓存Cache
    @RequestMapping("/remove")
    public void remove(Long id){
        personService.remove(id);
    }

    //缓存Cache
    @RequestMapping("/save")
    public PersonBean save(PersonBean person){
        PersonBean pagePeople = personService.save(person);
        return pagePeople;

    }

    //缓存Cache
    @RequestMapping("/findOne")
    public PersonBean findOne(PersonBean person){
        person = personService.findOne(person);
        return person;
    }

}
