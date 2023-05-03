package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.CustomAccessDeniedHandler;

/**
 * Это класс, который настраивает параметры безопасности для веб-приложения, включая
 * аутентификацию и авторизацию пользователей.
 * 
 * Аннотация `@Configuration` указывает, что класс является классом конфигурации, который предоставляет
 * определения bean-компонентов. Аннотация `@EnableWebSecurity` включает поддержку веб-безопасности
 * Spring Security и предоставляет необходимые конфигурации для защиты веб-приложения.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Эта функция возвращает экземпляр класса UserInfoDetailsService как UserDetailsService.
     * 
     * @return Возвращается новый экземпляр класса UserInfoDetailsService, который реализует интерфейс
     * UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoDetailsService();
    }

    /**
     * Эта функция возвращает объект BCryptPasswordEncoder, используемый для кодирования пароля.
     * 
     * @return Новый экземпляр класса BCryptPasswordEncoder, который представляет собой PasswordEncoder,
     * используемый для кодирования и декодирования паролей с использованием алгоритма хэширования BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Эта функция создает bean-компонент для пользовательского обработчика отказа в доступе в Java.
     * 
     * @return Возвращается bean-компонент типа AccessDeniedHandler, который представляет собой интерфейс,
     * используемый для обработки исключений отказа в доступе в Spring Security. Возвращаемая реализация —
     * это CustomAccessDeniedHandler.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Эта функция настраивает цепочку фильтров безопасности для HTTP-запросов, включая авторизацию, вход в
     * систему, выход из системы и обработку исключений.
     * 
     * @param http Объект HttpSecurity используется для настройки параметров безопасности для HTTP-запросов
     * в Spring Security. Он позволяет вам указать, какие запросы должны быть аутентифицированы, какие
     * должны быть разрешены без аутентификации, а также как обрабатывать процессы входа и выхода.
     * 
     * @return Возвращается объект SecurityFilterChain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/reg").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and().formLogin().loginPage("/login_page").defaultSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/login_page")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().build();
    }

    /**
     * Эта функция создает поставщика аутентификации, который использует службу сведений о пользователе и
     * кодировщик паролей для аутентификации.
     * 
     * @return Возвращается экземпляр интерфейса AuthenticationProvider. В частности, возвращается
     * экземпляр DaoAuthenticationProvider, который отвечает за аутентификацию пользователей на основе
     * данных, хранящихся в базе данных. Служба сведений о пользователе и кодировщик паролей, используемые
     * поставщиком проверки подлинности, также настраиваются в этом методе.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((userDetailsService()));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

}
