package com.online.cv.domain;

import com.online.cv.domain.common.UserDto;

public class SuccessAuthorizationResponse {
    private String token;
    private String tokenPrefix;
    private String tokenHeader;
    private Long expirationTs;
    private UserDto userDto;

    public SuccessAuthorizationResponse() {
    }

    public SuccessAuthorizationResponse(UserDto userDto, String token, String tokenPrefix, String tokenHeader, Long expirationTs) {
        this.userDto = userDto;
        this.token = token;
        this.tokenPrefix = tokenPrefix;
        this.tokenHeader = tokenHeader;
        this.expirationTs = expirationTs;
    }

    public UserDto getUser() {
        return userDto;
    }

    public String getToken() {
        return token;
    }

    public Long getExpirationTs() {
        return expirationTs;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }
}
