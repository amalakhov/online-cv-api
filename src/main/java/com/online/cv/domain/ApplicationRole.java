package com.online.cv.domain;

import java.util.stream.Stream;

public enum ApplicationRole {
    ADMIN("ADMIN"), USER("USER");

    private String role;

    ApplicationRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
