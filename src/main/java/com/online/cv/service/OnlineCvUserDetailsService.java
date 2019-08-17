package com.online.cv.service;

import com.online.cv.domain.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class OnlineCvUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public OnlineCvUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = loadApplicationUser(email);

        if (isNull(user)) {
            return null;
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().getApplicationRole());
        return new User(user.getEmail(), user.getPasswordHash(), authorities);
    }

    public UserDto loadApplicationUser(String email) {
        final Optional<UserDto> optionalUser = userService.findUserByEmail(email);
        return optionalUser.orElse(null);
    }
}
