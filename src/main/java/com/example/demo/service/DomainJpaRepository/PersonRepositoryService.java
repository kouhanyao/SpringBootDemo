package com.example.demo.service.DomainJpaRepository;

import com.example.demo.domain.PersonBean;
import com.example.demo.service.CustomerRepositoryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;//JpaRepository
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * spring data jpa 会自动注册该bean
 * Created by 寇含尧 on 2017/11/3.
 */
public interface PersonRepositoryService extends CustomerRepositoryService<PersonBean, Long> {
    //使用方法名查询
    List<PersonBean> findByName(String address, Sort sort);
    List<PersonBean> findByNameAndAddress(String name, String address);
    //使用@query查询，参数按照名称绑定
    @Query("select p.name from person p where p.name = :name and p.address= :address")
    List<PersonBean> withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);
    //使用@NamedQuery查询
    List<PersonBean> withNameAndAddressNamedQuery(String name, String address);
}
