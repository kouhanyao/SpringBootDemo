package com.example.demo.service.DomainJpaRepository;

import com.example.demo.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 寇含尧 on 2017/12/6.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    //根据用户名查询用户
    SysUser findByUsername(String username);
}
