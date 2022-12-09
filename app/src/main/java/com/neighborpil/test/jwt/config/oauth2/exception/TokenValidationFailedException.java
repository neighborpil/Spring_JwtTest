package com.neighborpil.test.jwt.config.oauth2.exception;

public class TokenValidationFailedException extends RuntimeException{

    public TokenValidationFailedException() {
        super("Failed to generate RedisAuthToken.");
    }

    private TokenValidationFailedException(String message) {
        super(message);
    }
}
