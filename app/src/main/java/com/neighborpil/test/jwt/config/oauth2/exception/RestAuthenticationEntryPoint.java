package com.neighborpil.test.jwt.config.oauth2.exception;

import com.neighborpil.test.jwt.config.oauth2.type.SecurityErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");

        if (exception == null) {
            // TODO 나중에 한번 더보기
            SecurityErrorCode errorCode = SecurityErrorCode.UNAUTHORIZED;
            setResponse(response, errorCode);
        } else if (exception.equals(SecurityErrorCode.EXPIRED_TOKEN.getCode())) {
            SecurityErrorCode errorCode = SecurityErrorCode.EXPIRED_TOKEN;
            setResponse(response, errorCode);
        }
    }

    private void setResponse(HttpServletResponse response, SecurityErrorCode errorCode) throws IOException {
        // TODO: 핸들러와 헤더가 다름 체크
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus());
        response.getWriter()
                .println("{\"success\":false,\"result\":null,\"errorMessage\":\"" + errorCode.getMessage() + "\"}");



    }
}
