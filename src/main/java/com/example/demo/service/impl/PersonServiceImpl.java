package com.example.demo.service.impl;

import com.example.demo.domain.PersonBean;
import com.example.demo.service.DomainJpaRepository.PersonRepositoryService;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 寇含尧 on 2017/11/4.
 */
@Repository
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepositoryService personRepositoryService;

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public PersonBean savePersonWithRollBack(PersonBean personBean) {
        personBean = personRepositoryService.save(personBean);
        if(personBean.getName().equals("寇含尧"))
            throw new IllegalArgumentException("寇含尧存在，数据回滚");
        return personBean;
    }

    @Override
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public PersonBean savePersonWithoutRollBack(PersonBean personBean) {
        personBean = personRepositoryService.save(personBean);
        if(personBean.getName().equals("寇含尧"))
            throw new IllegalArgumentException("寇含尧存在，数据不回滚");
        return personBean;
    }

    @Override
    @CachePut(value = "people",key = "#personBean.id")
//    无论怎样都会将方法的返回值放入缓存。spring boot中默认使用SimpleCacheConfiguration
//    value代表缓存名称，key为缓存中存储的键
    public PersonBean save(PersonBean personBean) {
        personBean = personRepositoryService.save(personBean);
        System.out.println("id,key为"+personBean.getId()+"的数据做了缓存");
        return personBean;
    }

    @Override
    @CacheEvict(value = "people")
    //将一条或多条数据从缓存中删除
    //没有指定key，则方法参数作为key
    public void remove(Long id) {
        System.out.println("删除了id,key为"+id+"的数据");
        personRepositoryService.delete(id);
    }

    @Override
    @Cacheable(value = "people",key = "#personBean.id")
    //先查看缓存中是否有数据，没有，则调用方法，并将返回值放入缓存中
    public PersonBean findOne(PersonBean personBean) {
        personBean = personRepositoryService.findOne(personBean.getId());
        System.out.println("id,key为"+personBean.getId()+"的数据做了缓存");
        return personBean;
    }
}
