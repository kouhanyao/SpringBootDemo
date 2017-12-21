package com.example.demo.ConfigSettings;

import com.example.demo.security.SysUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by 寇含尧 on 2017/12/6.
 */
@Configuration
public class WebSecutiryConfig extends WebSecurityConfigurerAdapter{  //扩展spring security配置需要继承WebSecurityConfigurerAdapter
    @Bean
    UserDetailsService sysUserService(){ //注册SysUserServiceImpl的bean
        return new SysUserServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService()); //添加我们自定义的user detail service认证

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http://www.tianshouzhi.com/api/tutorials/spring_security_4/266
        //https://segmentfault.com/q/1010000010307453/a-1020000010321874
        http.authorizeRequests()
                .antMatchers( "/bootstrap/**", "/jquery/**").permitAll()//静态资源
                .anyRequest().authenticated().and() //所有请求需要认证及登陆后才可以访问
                //// 登录地址，成功跳转地址，登录失败地址
                .formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error")
                .permitAll() //定制登陆行为，登陆页面任意访问
                .and()
                .logout().permitAll(); //定制注销行为，注销请求任意访问


    }

}
