package com.trickle.os.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Component
@PropertySource("config.properties")
public class TestComp {

    @Value("${resources}")
    private String resource;

    public TestComp() {
		System.out.println("ar" + resource);
	}
}
