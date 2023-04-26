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

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

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