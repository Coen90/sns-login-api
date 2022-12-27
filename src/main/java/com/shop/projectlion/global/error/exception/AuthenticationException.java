package com.shop.projectlion.global.error.exception;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
