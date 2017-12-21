package com.example.demo.ConfigSettings;

import com.example.demo.CustomerJpa.CustomRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by 寇含尧 on 2017/10/30.
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class,
        basePackages = {"com.example.demo"})
public class JpaConfig {

}
