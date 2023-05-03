package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Класс CustomAccessDeniedHandler, применяющий интерфейс `AccessDeniedHandler`, обрабатывает исключения, связанные с отказом в доступе, записывая
 * содержимое указанного HTML-файла в ответ.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Эта функция обрабатывает исключение отказа в доступе, записывая содержимое HTML-файла в ответ.
     * 
     * @param request Объект, представляющий HTTP-запрос, сделанный клиентом.
     * @param response Объект HttpServletResponse представляет собой ответ, который будет отправлен обратно
     * клиенту после обработки запроса сервером. Он содержит методы для установки статуса ответа,
     * заголовков и содержимого. В этом фрагменте кода объект ответа используется для установки типа
     * содержимого ответа на «текст/html».
     * @param exception Исключение AccessDeniedException, возникшее, когда пользователь попытался получить
     * доступ к ресурсу, на доступ к которому у него не было прав.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        if (!response.isCommitted()) {
            response.setContentType("text/html;charset=UTF-8");

            // read the contents of the HTML file
            InputStream inputStream = new FileInputStream("demo/src/main/resources/templates/error/403.html");
            String htmlContent = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();

            // write the HTML content to the response
            response.getWriter().write(htmlContent);
        }
    }
}