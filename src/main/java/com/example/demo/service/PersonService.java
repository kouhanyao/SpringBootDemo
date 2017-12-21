package com.example.demo.service;

import com.example.demo.domain.PersonBean;

/**
 * Created by 寇含尧 on 2017/11/4.
 */
public interface PersonService {
    //异常让数据回滚
    PersonBean savePersonWithRollBack(PersonBean personBean);
    //异常让数据不回滚
    PersonBean savePersonWithoutRollBack(PersonBean personBean);
    //cache
    PersonBean save(PersonBean personBean);
    //cache
    void remove(Long id);
    //cache
    PersonBean findOne(PersonBean personBean);
}
