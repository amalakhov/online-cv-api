package com.online.cv.domain.common;

public class NewUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String confirmPassword;

    public NewUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
