package com.online.cv.domain;

public class AuthorizationRequest {
    private String email;
    private String password;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthorizationRequest{" +
                "email='" + email + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                '}';
    }
}
