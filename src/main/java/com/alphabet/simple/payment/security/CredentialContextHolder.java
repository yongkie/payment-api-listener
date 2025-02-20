package com.alphabet.simple.payment.security;

import com.alphabet.simple.payment.interceptor.dto.JwtTokenDto;
import com.alphabet.simple.payment.interceptor.dto.UserPermissionDto;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class CredentialContextHolder {
    private static final ThreadLocal<JwtTokenDto> contextHolder = new ThreadLocal<>();

    public static JwtTokenDto getContext(){
        var jwtTokenDto = contextHolder.get();
        return Optional.of(jwtTokenDto).orElse(null);
    }

    public static void setContext(JwtTokenDto jwtTokenDto){
        contextHolder.set(jwtTokenDto);
    }

    public static String getBusinessServiceClientId(){
        var jwtTokenDto = contextHolder.get();
        return Optional.of(jwtTokenDto)
                .map(JwtTokenDto::getUser)
                .map(UserPermissionDto::getBusinessServiceClientId)
                .orElse(null);
    }
}
