package com.example.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文通用类
 * <p>
 * Created by 寇含尧 on 2017/11/12.
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext currentContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        currentContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return currentContext;
    }

    /**
     * 按名字获取bean
     *
     * @param name
     * @param <T>
     * @return 指定的bean实例
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) currentContext.getBean(name);
    }

    /**
     * 按类型获取bean
     *
     * @param requiredType 类型
     * @param <T>          类型
     * @return 指定的bean实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        return currentContext.getBean(requiredType);
    }

    /**
     * 按类型和参数获取bean
     *
     * @param requiredType 类型
     * @param args         参数
     * @param <T>          类型
     * @return 指定的bean实例
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return currentContext.getBean(requiredType, args);
    }
}
