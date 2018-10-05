package com.clickdebit.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;
    
    public static ApplicationContext getApplicationContext() {
        return CONTEXT;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        CONTEXT = ctx;
    }

    public static Object getBean(String name) {
        return CONTEXT.getBean(name);
    }

    public static <T> T getBeanByClass(Class<T> clz) {
        return CONTEXT.getBean(clz);
    }
}