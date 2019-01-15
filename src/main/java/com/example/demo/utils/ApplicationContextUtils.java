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

    public static <T> T getBean(String name) throws BeansException {
        return (T) currentContext.getBean(name);
    }

    /**
     * 按类型获取Bean实例
     *
     * @param requiredType 类型
     * @param <T>          类型
     * @return Bean实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        return currentContext.getBean(requiredType);
    }

    /**
     * 按类型和参数获取Bean实例
     *
     * @param requiredType 类型
     * @param args         参数
     * @param <T>          类型
     * @return 指定的Bean实例
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return currentContext.getBean(requiredType, args);
    }
}
