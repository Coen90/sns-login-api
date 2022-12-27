package com.shop.projectlion.global.error.exception;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

}
