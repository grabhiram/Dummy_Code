package com.clickdebit.application;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration 
////@ComponentScan("com.concretepage") 
//@EnableWebMvc   
public class AppConfig extends WebMvcConfigurerAdapter  {  
//	@Bean
//	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
//	    return new DeviceResolverHandlerInterceptor();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//	    registry.addInterceptor(deviceResolverHandlerInterceptor());
//	}
//	
//	@Bean
//	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
//	    return new DeviceHandlerMethodArgumentResolver();
//	}
//
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//	    argumentResolvers.add(deviceHandlerMethodArgumentResolver());
//	}
	
} 