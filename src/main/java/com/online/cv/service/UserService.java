package com.online.cv.service;

import com.online.cv.db.tables.daos.UserDao;
import com.online.cv.db.tables.daos.UserRoleDao;
import com.online.cv.db.tables.pojos.User;
import com.online.cv.db.tables.pojos.UserRole;
import com.online.cv.domain.common.NewUserRequest;
import com.online.cv.domain.common.UserDto;
import com.online.cv.domain.common.UserStatus;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.domain.exception.CreateUserException;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String DEFAULT_ROLE_NAME = "User";

    private final UserDao userDao;
    private final UserRoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(Configuration configuration,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userDao = new UserDao(configuration);
        this.roleDao = new UserRoleDao(configuration);
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<ErrorCode.Authorization> createUser(NewUserRequest request) {
        if (isNull(request)) {
            logger.error("Request body is null");
            return Optional.of(ErrorCode.Authorization.REQUEST_BODY_IS_NULL);
        }

        final User user = userDao.fetchOneByEmail(request.getEmail());
        if (!isNull(user)) {
            logger.error("User with the specified email already exists");
            return Optional.of(ErrorCode.Authorization.USER_ALREADY_EXISTS);
        }

        if (!isNullOrEmpty(request.getPassword())
                && Objects.equals(request.getPassword(), request.getConfirmPassword())) {

            final UserRole defaultRole = roleDao.fetchOneByName(DEFAULT_ROLE_NAME);
            final String password = passwordEncoder.encode(request.getPassword());

            final User newUser = new User(null,
                                        request.getLastName(),
                                        request.getFirstName(),
                                        request.getMiddleName(),
                                        request.getEmail(),
                                        defaultRole.getId(),
                                        null,
                                        password,
                                        UserStatus.ACTIVE.name());

            userDao.insert(newUser);
            logger.info("createUser::user = {} created", request.getEmail());

            return Optional.empty();
        } else {
            logger.error("Passwords don't match");
            return Optional.of(ErrorCode.Authorization.PASSWORDS_DONT_MATCH);
        }
    }

    public Optional<UserDto> findUserByEmail(String email) {
        final User user = userDao.fetchOneByEmail(email);
        final UserRole role = roleDao.fetchOneById(user.getRoleId());

        return Optional.of(UserDto.instance(user, role, null));

    }

    public Optional<UserDto> resolveUser(Principal principal) {
        return findUserByEmail(principal.getName());
    }
}
