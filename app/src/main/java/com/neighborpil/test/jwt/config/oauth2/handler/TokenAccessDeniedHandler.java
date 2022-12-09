package com.neighborpil.test.jwt.config.oauth2.handler;

import com.neighborpil.test.jwt.config.oauth2.type.SecurityErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        SecurityErrorCode errorCode = SecurityErrorCode.FORBIDDEN;

        response.setHeader("content-type", "application/json");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus());
        response.getWriter()
                .write("{\"success\":false,\"result\":null,\"errorMessage\":\"" + errorCode.getMessage() + "\"}");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
