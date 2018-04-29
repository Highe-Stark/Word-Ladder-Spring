package com.stark.wordladder;

import com.stark.aop.BeforeLoginInterceptor;
import com.stark.aop.BeforeServiceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public void addInterceptor(InterceptorRegistry registry) {
        registry.addInterceptor(new BeforeLoginInterceptor()).addPathPatterns("/login");
        registry.addInterceptor(new BeforeServiceInterceptor()).addPathPatterns("/wordladder");
    }
}
