package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * Это основной класс для приложения Spring Boot, которое сканирует пакет «com.example.demo» на наличие
 * компонентов.
 */
@SpringBootApplication
@ComponentScan("com.example.demo")
public class ResourcesManagerApplication extends SpringBootServletInitializer{
    
    /**
     * Это основная функция, которая запускает класс ResourcesManagerApplication в приложении Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(ResourcesManagerApplication.class, args);
    }
}
