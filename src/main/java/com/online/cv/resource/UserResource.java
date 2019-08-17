package com.online.cv.resource;

import com.online.cv.domain.common.NewUserRequest;
import com.online.cv.domain.common.UserDto;
import com.online.cv.domain.exception.CreateUserException;
import com.online.cv.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class UserResource {
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/hey")
    public ResponseEntity<String> heyMan(Principal principal) {
        final Optional<UserDto> optionalUser = userService.resolveUser(principal);

        if (optionalUser.isPresent()) {
            final UserDto user = optionalUser.get();
            String message = "Hey " + user.getEmail() + "! Your id is '" + user.getId() + "'";
            return ResponseEntity.ok().body(message);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/user/registration")
    public ResponseEntity<String> createUser(@RequestBody NewUserRequest request) {
        try {
            userService.createUser(request);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CreateUserException ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
