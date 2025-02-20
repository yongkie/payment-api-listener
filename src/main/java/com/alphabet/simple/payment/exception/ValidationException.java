package com.alphabet.simple.payment.exception;

import com.alphabet.simple.payment.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ResponseCode responseCode;
}
