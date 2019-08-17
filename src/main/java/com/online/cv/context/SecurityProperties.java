package com.online.cv.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "file:/etc/online-cv/jwt.properties")
public class SecurityProperties {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expire.millis}")
    private Integer expireMillis;

    @Value("${jwt.token.prefix}")
    private String prefix;

    @Value("${jwt.header}")
    private String header;

    public String getSecret() {
        return secret;
    }

    public Integer getExpireMillis() {
        return expireMillis;
    }

    public String getPrefix() {
        return prefix + " ";
    }

    public String getHeader() {
        return header;
    }
}
