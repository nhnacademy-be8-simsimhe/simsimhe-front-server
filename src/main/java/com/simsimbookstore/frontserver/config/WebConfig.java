package com.simsimbookstore.frontserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("loginForm");
        registry.addViewController("/users/myPage").setViewName("/users/myPage");
//        registry.addViewController("/users/myPage").setViewName("users/myPage");
    }
}
