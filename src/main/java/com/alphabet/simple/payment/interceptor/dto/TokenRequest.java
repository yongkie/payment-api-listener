package com.alphabet.simple.payment.interceptor.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequest {
    private String clientId;
    private String roleName;
    private String userFullName;
    private Integer roleId;
    private String userEmail;
    private String businessClientId;
    private String businessClientName;
}
