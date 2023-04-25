package com.example.demo.security;

// import com.example.demo.security.UserInfoDetailsService;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/reg").permitAll()
                .requestMatchers("/reg_admin").hasAnyRole("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and().formLogin().loginPage("/login_page").defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login_page")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((userDetailsService()));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

}
