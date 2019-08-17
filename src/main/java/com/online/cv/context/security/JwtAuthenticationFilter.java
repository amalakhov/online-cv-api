package com.online.cv.context.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.cv.context.SecurityProperties;
import com.online.cv.db.Keys;
import com.online.cv.domain.AuthorizationRequest;
import com.online.cv.domain.SuccessAuthorizationResponse;
import com.online.cv.domain.common.UserDto;
import com.online.cv.service.OnlineCvUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final OnlineCvUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   SecurityProperties securityProperties,
                                   OnlineCvUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthorizationRequest authorizationRequest = new ObjectMapper().readValue(request.getInputStream(), AuthorizationRequest.class);
            logger.info("attemptAuthentication:: authorizationRequest = {}", authorizationRequest);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getEmail(), authorizationRequest.getPassword()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        LocalDateTime expirationTimeUTC = LocalDateTime.now(ZoneOffset.UTC).plus(securityProperties.getExpireMillis(), ChronoUnit.MILLIS);
        User user = ((User) authResult.getPrincipal());

        final String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expirationTimeUTC.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, securityProperties.getSecret().getBytes())
                .compact();

        UserDto userDto = userDetailsService.loadApplicationUser(user.getUsername());
        SuccessAuthorizationResponse authorizationResponse = new SuccessAuthorizationResponse(userDto, token,
                securityProperties.getPrefix(), securityProperties.getHeader(), expirationTimeUTC.toEpochSecond(ZoneOffset.UTC));

        response.addHeader(securityProperties.getHeader(), securityProperties.getPrefix() + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(authorizationResponse));
    }
}
