package com.alphabet.simple.payment.enums;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum ResponseCode {
    SUCCESS("0000","Success"),
    UNAUTHORIZED_ACCESS("0013","Unauthorized Access");

    private final String code;
    private final String message;

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }
    public String code(){
        return code;
    }

    public String message(){
        return message;
    }

    public ResponseCode byCode(String code) {
        return Stream.of(values())
                .filter(isEquals())
                .findAny()
                .orElse(null);
    }

    private Predicate<ResponseCode> isEquals(){
        return responseCode -> Optional.ofNullable(responseCode)
                .map(thisObject -> thisObject.code)
                .map(code::equals)
                .orElse(false);
    }
}
