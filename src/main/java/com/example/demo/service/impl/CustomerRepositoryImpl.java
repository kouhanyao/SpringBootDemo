package com.example.demo.service.impl;

import com.example.demo.CustomerJpa.CustomerSpecification;
import com.example.demo.service.CustomerRepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * SimpleJpaRepository为JpaRepository的实现类
 * Created by 寇含尧 on 2017/11/3.
 */
public class CustomerRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements
        CustomerRepositoryService<T, ID>{
    private final EntityManager entityManager;

    public CustomerRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Page<T> findByAuto(T example, Pageable pageable) {
        return findAll(CustomerSpecification.byAuto(entityManager, example),pageable);
    }

}
