package com.trickle.os.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
@Component
//@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer {

    @Value("${config.resources}")
    private String resources;

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	int cachePeriod = 3600;
    	System.out.println("resource = " + resources);
    	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(cachePeriod)
                .resourceChain(true).addResolver(defaultImageResolver("/default.png"));
        
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(cachePeriod); 
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(cachePeriod); 
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCachePeriod(cachePeriod);
    	registry.addResourceHandler("/resources/**").addResourceLocations("file:/"+resources+"/").setCachePeriod(cachePeriod);
    	registry.addResourceHandler("/resources/images/**").addResourceLocations("file:/"+resources+"/images/").setCachePeriod(cachePeriod)
    			.resourceChain(true).addResolver(defaultImageResolver("/default.png"));
    }
	
	private PathResourceResolver defaultImageResolver(String imagePath) {
		return new PathResourceResolver() {
            @Override
            protected Resource getResource(String resourcePath, Resource location) throws IOException {
                Resource requestedResource = location.createRelative(resourcePath);
                return requestedResource.exists() && requestedResource.isReadable() ?
                        requestedResource : location.createRelative("/default.png");
            }
        };
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        registry.addInterceptor(webContentInterceptor).addPathPatterns("/**");
	}
}