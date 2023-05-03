package com.example.demo.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Это класс, представляющий информацию о пользователе с полями для идентификатора, имени, пароля
 * и ролей.
 * 
 * Аннотация `@Entity` указывает, что этот класс
 * является сущностью JPA, что означает, что он может быть сохранен в базе данных. Аннотация `@Data`
 * взята из библиотеки Lombok и генерирует шаблонный код для геттеров, сеттеров и других методов.
 * Аннотации `@AllArgsConstructor` и `@NoArgsConstructor` генерируют конструкторы с аргументами и без
 * аргументов соответственно.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    /** Это определение первичного ключа сущности UserInfo. Аннотация `@Id` указывает, что поле `id`
    * является первичным ключом, а аннотация `@GeneratedValue` указывает, как должно быть сгенерировано
    * значение первичного ключа. В этом случае используется стратегия `GenerationType.IDENTITY`, что
    * означает, что значение первичного ключа генерируется базой данных.
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**  Эта частная переменная строкого типа представляет имя пользователя.
    */
    private String name;


    /** Эта частная переменная строкого типа представляет пароль пользователя. 
    */
    private String password;


    /** Эта частная переменная строкого типа представляет роли пользователя и используется для хранения ролей в
    * базе данных.
    */
    private String roles;

    /**
     * Эта функция возвращает пароль в виде строки.
     * 
     * @return Значение `String`, которое является паролем.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Функция возвращает имя пользователя.
     * 
     * @return Строка с именем пользователя.
     */
    public String getName(){
        return name;
    }

}
