package com.neighborpil.test.jwt.config.oauth2.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {

    EXPIRED_TOKEN(HttpServletResponse.SC_BAD_REQUEST, "Expired token request", "EXPIRED_TOKEN"),
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized request", "UNAUTHORIZED"),
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "Wrong authority request", "FORBIDDEN");

    private final int httpStatus;
    private final String message;
    private final String code;

}
