package com.online.cv.context.security;

import com.online.cv.context.SecurityProperties;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.service.JsonMapperService;
import com.online.cv.service.OnlineCvUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
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
    private final JsonMapperService jsonMapperService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  SecurityProperties securityProperties,
                                  OnlineCvUserDetailsService userDetailsService,
                                  JsonMapperService jsonMapperService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
        this.jsonMapperService = jsonMapperService;
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

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (ExpiredJwtException tokenExpired) {
            logger.error(tokenExpired.getMessage(), tokenExpired);
            final String body = jsonMapperService.toJSON(ErrorCode.Authorization.TOKEN_EXPIRED);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(body);
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            final String body = jsonMapperService.toJSON(ErrorCode.Authorization.ACCESS_DENIED);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(body);
            response.getWriter().flush();
            response.getWriter().close();
        }
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
