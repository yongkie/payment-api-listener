package com.alphabet.simple.payment.exception.dto;

import com.alphabet.simple.payment.enums.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T data;

    public BaseResponse<T> generateResponse(ResponseCode responseCode) {
        this.setResponseCode(responseCode.code());
        this.setResponseMessage(responseCode.message());
        return this;
    }

    public BaseResponse<T> generateResponse(String code, String message) {
        this.setResponseCode(code);
        this.setResponseMessage(message);
        return this;
    }

    public BaseResponse<T> generateResponse(ResponseCode responseCode, T data) {
        this.setResponseCode(responseCode.code());
        this.setResponseMessage(responseCode.message());
        this.setData(data);
        return this;
    }
}
