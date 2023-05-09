package com.trickle.os.mvc.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new LoginInterceptor())
//        .excludePathPatterns("/**");
        
        .addPathPatterns("/**");//		.excludePathPatterns("/js/**","/images/**","/css/**","/test/**","/","/resources/**","/ajax/"
//        "/page/**","/process/**","/user/**","/os-home","/index","/home")
        ;
    }
}