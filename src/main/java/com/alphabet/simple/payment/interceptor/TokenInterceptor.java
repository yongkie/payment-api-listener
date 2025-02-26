package com.alphabet.simple.payment.interceptor;

import com.alphabet.simple.payment.annotations.Permission;
import com.alphabet.simple.payment.constant.GlobalConstant;
import com.alphabet.simple.payment.enums.ResponseCode;
import com.alphabet.simple.payment.exception.ValidationException;
import com.alphabet.simple.payment.exception.dto.BaseResponse;
import com.alphabet.simple.payment.interceptor.dto.JwtTokenDto;
import com.alphabet.simple.payment.interceptor.dto.TokenRequest;
import com.alphabet.simple.payment.interceptor.dto.UserPermissionDto;
import com.alphabet.simple.payment.security.CredentialContextHolder;
import com.alphabet.simple.payment.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
    private final JsonUtils jsonUtils;

    @Value("${server.servlet.context-path}")
    private String urlContextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestTokenHeader = request.getHeader(GlobalConstant.AUTHORIZATION);
        log.info("auth {}",requestTokenHeader);
        if(requestTokenHeader == null){
            generateErrorUnauthorized(response);
            return false;
        }
        String getRequestURIPath = request.getRequestURI();

        try {
            String[] splitToken = requestTokenHeader.split("[.]");
            String decoded = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);

            JwtTokenDto jwtTokenDto = jsonUtils.convertJson(decoded, JwtTokenDto.class);
            UserPermissionDto user = jwtTokenDto.getUser();

            String businessServiceClientId = user.getBusinessServiceClientId();
            String roleName = user.getRoleName();
            Integer roleId = user.getRoleId();
            String userFullName = user.getUserFullName();
            String userEmail = user.getUserEmail();
            String businessId = user.getBusinessClientId();
            String businessName = user.getBusinessClientName();

            request.setAttribute(GlobalConstant.CLIENTID, businessServiceClientId);

            TokenRequest tokenRequest = TokenRequest.builder()
                    .clientId(businessServiceClientId)
                    .roleName(roleName).roleId(roleId)
                    .userFullName(userFullName).userEmail(userEmail)
                    .businessClientId(businessId).businessClientName(businessName)
                    .build();
            request.setAttribute(GlobalConstant.TOKENREQUEST, tokenRequest);

            var permissionHandler = getPermission(handler);
            if(permissionHandler != null){
                log.info("endpoint permission {} with type: {} and roles: {}",
                        permissionHandler.value(), permissionHandler.type(), permissionHandler.roles());

                if(!isAuthorized(requestTokenHeader, permissionHandler)){
                    log.warn("Request to {}, Denied", getRequestURIPath);
                    generateErrorUnauthorized(response);
                    return false;
                }
            }

            log.debug("Request to {}, Granted", getRequestURIPath);
            return true;

        }
        catch (Exception e){
            log.warn("Request to {}, Denied, Invalid Token Format", getRequestURIPath);
            generateErrorUnauthorized(response);
            return false;
        }
    }

    private boolean isAuthorized(String auth, Permission permission){
        try {
            return Optional.ofNullable(getTokenPermission(auth))
                    .map(jwtTokenDto -> {
                        // store on contect credential
                        CredentialContextHolder.setContext(jwtTokenDto);
                        return jwtTokenDto.getUser();
                    })
                    .map(user -> {
                        var allowed = true;

                        if(!permission.value().isEmpty()){
                            var jwtPermissions = user.getPermission();
                            allowed = jwtPermissions.stream().anyMatch(permission.value()::equalsIgnoreCase);
                        }

                        if(!permission.type().isEmpty() && allowed){
                            allowed = user.getUserType().equalsIgnoreCase(permission.type());
                        }

                        if(permission.roles().length > 0 && allowed){
                            allowed = Arrays.stream(permission.roles()).anyMatch(user.getRoleName()::equalsIgnoreCase);
                        }

                        return allowed;
                    }).orElse(Boolean.FALSE);
        } catch (Exception e) {
            throw new ValidationException(ResponseCode.UNAUTHORIZED_ACCESS);
        }
    }

    private JwtTokenDto getTokenPermission(String auth){
        var token = auth.split(" ")[1];
        var tokenPayload = token.split("[.]")[1];
        // decode jwt token payload
        var decoded = new String(Base64.getDecoder().decode(tokenPayload), StandardCharsets.UTF_8);

        var payload = jsonUtils.convertJson(decoded, JwtTokenDto.class);
        log.info("payload authorization {}", payload);

        return payload;
    }

    private void generateErrorUnauthorized(HttpServletResponse response) throws IOException {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.generateResponse(ResponseCode.UNAUTHORIZED_ACCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.print(jsonUtils.toJsonString(baseResponse));
        out.close();
    }

    private Permission getPermission(Object handler){
        Method method = getMethod(handler);
        if(method != null && method.isAnnotationPresent(Permission.class)){
            return method.getAnnotation(Permission.class);
        }
        return null;
    }

    private Method getMethod(Object handler){
        HandlerMethod handlerMethod;

        if(handler instanceof HandlerMethod){
            handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethod();
        }
        return null;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Finish");
    }
}
