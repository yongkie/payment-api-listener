package com.alphabet.simple.payment.interceptor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserPermissionDto {
    private String businessServiceClientId;
    private String businessServiceClientName;
    private Integer roleId;
    private String roleName;
    private String userFullName;
    private String menuName;
    private List<String> permission;
    private String businessClientName;
    private String userEmail;
    private String userType;
    private Integer userId;
    private String businessClientId;
}
