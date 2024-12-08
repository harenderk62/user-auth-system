package com.harender.user_auth_system.exceptions;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
