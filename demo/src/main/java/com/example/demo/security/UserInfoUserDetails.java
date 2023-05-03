package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс UserInfoUserDetails реализует UserDetails и предоставляет методы для получения информации о
 * пользователе, такой как имя, пароль и полномочия.
 */
public class UserInfoUserDetails implements UserDetails {

    /** Эта приватная строковая переменная используется для хранения имени пользователя. */
    private String name;

    /** Эта приватная строковая переменная используется для хранения пароля пользователя. */
    private String password;

    
    /** Эта приватная переменная в виде списка используется для хранения полномочий */
    private List<GrantedAuthority> authorities;

    /** Это конструктор для класса UserInfoUserDetails. Он инициализирует поля «имя» и «пароль» объекта «UserInfoUserDetails» соответствующими
    * значениями из объекта «UserInfo». Он также инициализирует поле `authorities`, разбивая строку
    * `roles` из объекта `UserInfo` на список строк, сопоставляя каждую строку с объектом
    * `SimpleGrantedAuthority` и собирая полученные объекты в список. Затем этот список присваивается полю
    * «полномочия» объекта «UserInfoUserDetails».
    *
    * @param userInfo Информация о пользователе.
    */
    public UserInfoUserDetails(UserInfo userInfo) {
        name = userInfo.getName();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Эта функция возвращает набор предоставленных полномочий.
     * 
     * @return Метод возвращает коллекцию объектов, реализующих интерфейс GrantedAuthority. Конкретный тип
     * объектов в коллекции определяется реализацией метода.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Эта функция возвращает пароль в виде строки.
     * 
     * @return Значение `String`, которое является паролем.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Эта функция возвращает имя пользователя.
     * 
     * @return Строка с именем пользователя.
     */
    @Override
    public String getUsername() {
        return name;
    }

    /**
     * Функция возвращает логическое значение, указывающее, не истек ли срок действия учетной записи.
     * 
     * @return Метод возвращает логическое значение «истина».
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Функция возвращает true, если учетная запись не заблокирована.
     * 
     * @return Метод возвращает логическое значение «истина».
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Функция возвращает значение true, если срок действия учетных данных пользователя не истек.
     * 
     * @return Метод возвращает логическое значение «истина».
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Эта функция всегда возвращает true - статус аккаунта пользователя.
     * 
     * @return Метод возвращает логическое значение «истина».
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
