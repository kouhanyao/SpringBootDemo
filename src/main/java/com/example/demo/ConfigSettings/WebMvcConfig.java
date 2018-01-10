package com.example.demo.ConfigSettings;

import com.example.demo.Interceptor.RequestParamsInterceptor;
import com.example.demo.configBean.SpringUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * Created by 寇含尧 on 2017/10/24.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 视图解析，用来映射路径和实际页面的位置
     *
     * @return
     */
    /*@Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/templates/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        http://www.cnblogs.com/vicis/articles/6077839.html
        registry.addViewController("/homepage").setViewName("homepage");
        registry.addViewController("/ws").setViewName("ws");
        registry.addViewController("/login").setViewName("login");
        /*registry.addViewController("/home").setViewName("myhome");
        registry.addViewController("/hello").setViewName("helloworld");
        registry.addRedirectViewController("/home", "/hello");
        registry.addStatusController("/detail", HttpStatus.BAD_REQUEST);*/
    }


    /*自动转向https*/
//    @Bean
//    public EmbeddedServletContainerFactory servletContainerFactory() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
    /*添加tomcat的connector*/
//    @Bean
//    public Connector httpConnector() {
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
    /*自动转向https*/


    /**
     * 注册SpringUtils
     */
    @Bean
    public SpringUtils springUtil() {
        return new SpringUtils();
    }

    /**
     * 注册RequestParamsInterceptor
     *
     * @return
     */
    @Bean
    public RequestParamsInterceptor requestParamsInterceptor() {
        return new RequestParamsInterceptor();
    }

    /**
     * 配置上传文件参数
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize("40KB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("2512KB");
        // Sets the directory location where files will be stored.
        // factory.setLocation("路径地址");
        return factory.createMultipartConfig();
    }

    /**
     * 配置路径映射
     * @param dispatcherServlet
     * @return
     */
    /*@Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.do");
        return registration;
    }*/
}
