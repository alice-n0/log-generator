package com.hj.pay_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayException extends RuntimeException {

    private final PayErrorCode errorCode;

    public PayException(PayErrorCode errorCode, Throwable cause) {
        super(errorCode.name(), cause);
        this.errorCode = errorCode;
    }
}
