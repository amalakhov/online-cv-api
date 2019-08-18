package com.online.cv.resource;

import com.online.cv.domain.error.ErrorCode;
import com.online.cv.service.JsonMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final JsonMapperService jsonMapperService;

    public AuthenticationEntryPointHandler(JsonMapperService jsonMapperService) {
        this.jsonMapperService = jsonMapperService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        if (!isNull(authException)) {
            try {
                final String body = jsonMapperService.toJSON(ErrorCode.Authorization.FULL_AUTHENTICATION_REQUIRED);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().write(body);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
