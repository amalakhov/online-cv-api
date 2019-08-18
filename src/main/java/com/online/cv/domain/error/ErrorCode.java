package com.online.cv.domain.error;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorCode {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static enum Common {
        COMMON_ERROR(0, "Something went wrong");

        private Integer code;
        private String description;

        Common(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static enum Authorization {
        USER_ALREADY_EXISTS(100, "User with the specified email already exists"),
        PASSWORDS_DONT_MATCH(101, "Passwords don't match"),
        USER_NOT_FOUND(102, "User with the specified email and password doesn't exist"),
        REQUEST_BODY_IS_NULL(103, "Request body is null"),
        CREATE_USER_FAILED(104, "Registration failed"),
        TOKEN_EXPIRED(105, "Token has been expired"),
        ACCESS_DENIED(106, "Access denied"),
        FULL_AUTHENTICATION_REQUIRED(107, "Full authentication is required");

        private Integer code;
        private String description;

        Authorization(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

}
