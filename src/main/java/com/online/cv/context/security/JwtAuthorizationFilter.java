package com.online.cv.context.security;

import com.online.cv.context.SecurityProperties;
import com.online.cv.service.OnlineCvUserDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.hibernate.validator.internal.util.StringHelper.isNullOrEmptyString;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final OnlineCvUserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  SecurityProperties securityProperties,
                                  OnlineCvUserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenValue = request.getHeader(securityProperties.getHeader());

        if (isNullOrEmptyString(tokenValue) || !tokenValue.startsWith(securityProperties.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        final String headerValue = request.getHeader(securityProperties.getHeader());
        String token = headerValue.replace(securityProperties.getPrefix(), "");

        final String username = Jwts.parser()
                                    .setSigningKey(securityProperties.getSecret().getBytes())
                                    .parseClaimsJws(token)
                                    .getBody()
                                    .getSubject();

        if (isNullOrEmptyString(headerValue) || !headerValue.startsWith(securityProperties.getPrefix())) {
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (isNull(userDetails)) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
