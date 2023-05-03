package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Это класс, который реализует интерфейс UserDetailsService и загружает сведения о пользователе
 * из UserInfoRepository на основе предоставленного имени пользователя.
 */
@Component
public class UserInfoDetailsService implements UserDetailsService {


    /** `@Autowired` — это аннотация в Spring, позволяющая автоматически внедрять зависимости. В данном
    * случае он внедряет экземпляр UserInfoRepository в класс UserInfoDetailsService, который используется
    * для извлечения информации о пользователе из базы данных.
    */
    @Autowired
    private UserInfoRepository repo;

    /**
     * Эта функция загружает сведения о пользователе по имени пользователя из репозитория и возвращает
     * объект UserDetails или выдает исключение, если пользователь не найден.
     * 
     * @param username Имя пользователя, данные которого загружаются.
     * 
     * @return Метод возвращает экземпляр UserDetails, который либо создается из объекта UserInfo,
     * полученного из репозитория, либо генерирует исключение UsernameNotFoundException, если пользователь
     * не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repo.findByName(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
    }
}
