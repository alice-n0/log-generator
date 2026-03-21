package com.hj.pay_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayException extends RuntimeException {

    private final PayErrorCode errorCode;

    public PayException(PayErrorCode errorCode, Throwable cause) {
        super(errorCode.name(), cause);
        this.errorCode = errorCode;
    }
}
