package com.online.cv.resource;

import com.online.cv.domain.error.ErrorCode;
import com.online.cv.service.JsonMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final JsonMapperService jsonMapperService;

    public CustomAccessDeniedHandler(JsonMapperService jsonMapperService) {
        this.jsonMapperService = jsonMapperService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) {
        try {
            final String body = jsonMapperService.toJSON(ErrorCode.Authorization.ACCESS_DENIED);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(body);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
