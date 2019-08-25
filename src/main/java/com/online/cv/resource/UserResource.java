package com.online.cv.resource;

import com.online.cv.db.tables.pojos.Files;
import com.online.cv.domain.common.NewUserRequest;
import com.online.cv.domain.common.UpdateUserPhotoRequest;
import com.online.cv.domain.common.UserDto;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.service.FileStorageService;
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

import static com.online.cv.domain.error.ErrorCode.Common.COMMON_ERROR;
import static com.online.cv.domain.error.ErrorCode.Common.USER_NOT_FOUND;

@RestController
public class UserResource {
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public UserResource(UserService userService,
                        FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(path = "/user/hey")
    public ResponseEntity<String> hey(Principal principal) {
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
    public ResponseEntity<ErrorCode.Authorization> createUser(@RequestBody NewUserRequest request) {
        try {
            final Optional<ErrorCode.Authorization> error = userService.createUser(request);
            return error.map(authorization -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(authorization)).orElseGet(() -> ResponseEntity.status(HttpStatus.OK).build());

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCode.Authorization.CREATE_USER_FAILED);
        }
    }

    @PostMapping(path = "/user/photo/update/")
    public ResponseEntity updateUserPhoto(@RequestBody UpdateUserPhotoRequest request,
                                          Principal principal) {
        try {
            final Optional<UserDto> optionalUser = userService.resolveUser(principal);

            if (optionalUser.isPresent()) {
                final UserDto userDto = optionalUser.get();
                final Files file = fileStorageService.findById(request.getFileId());

                if (file.getOwnerUserId().equals(userDto.getId())) {
                    final boolean result = userService.updateUserPhoto(userDto, request.getFileId());

                    if (result) {
                        return ResponseEntity.ok().build();
                    } else {
                        throw new RuntimeException("Update user photo failed");
                    }

                } else {
                    throw new RuntimeException("Access denied");
                }

            } else {
                return ResponseEntity.badRequest().body(USER_NOT_FOUND);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(COMMON_ERROR);
        }
    }
}
