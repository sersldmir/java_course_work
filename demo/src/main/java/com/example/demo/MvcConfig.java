package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Это класс конфигурации Java, который реализует интерфейс WebMvcConfigurer и переопределяет метод
 * addViewControllers.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    /**
     * Это пустой метод, который переопределяет метод addViewControllers в классе
     * ViewControllerRegistry в Java.
     * 
     * @param registry Параметр реестра — это объект класса ViewControllerRegistry, который
     * используется для регистрации контроллеров представлений в Spring MVC. Он предоставляет методы
     * для регистрации контроллеров представления для определенных URL-адресов или шаблонов пути. Метод
     * addViewControllers() используется для добавления контроллеров представлений в реестр.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){}
}
