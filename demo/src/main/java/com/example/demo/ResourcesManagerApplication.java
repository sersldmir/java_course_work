package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example.demo")
public class ResourcesManagerApplication extends SpringBootServletInitializer{
    
    public static void main(String[] args) {
        SpringApplication.run(ResourcesManagerApplication.class, args);
    }
}
