package com.trickle.os.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	int cachePeriod = 3600;
    	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(cachePeriod)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ?
                                requestedResource : location.createRelative("/default.png");
                    }
                });
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(cachePeriod); 
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(cachePeriod); 
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCachePeriod(cachePeriod);
    }
}