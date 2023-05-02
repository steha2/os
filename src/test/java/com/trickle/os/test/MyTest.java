package com.trickle.os.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@TestPropertySource(locations = "classpath:config.properties")
public class MyTest {

    @Value("${config.resources}")
    private String resources;

    @Test
    public void test() {
        System.out.println("resources: " + resources);
    }

}
