package com.alphabet.simple.payment.interceptor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDto {
    private String sub;
    private Integer exp;
    private UserPermissionDto user;
    private Integer iat;
}
