package com.online.cv.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.cv.db.tables.pojos.User;
import com.online.cv.db.tables.pojos.UserRole;
import com.online.cv.domain.upload.UploadedFile;

public class UserDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private UserRole role;
    private UploadedFile photo;
    private String passwordHash;
    private UserStatus status;

    public static UserDto instance(User user, UserRole role, UploadedFile photo) {
        return new UserDto(user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getEmail(),
                role,
                photo,
                user.getPasswordHash(),
                UserStatus.valueOf(user.getStatus()));
    }

    public UserDto(Integer id,
                   String lastName,
                   String firstName,
                   String middleName,
                   String email,
                   UserRole role,
                   UploadedFile photo,
                   String passwordHash,
                   UserStatus status) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.role = role;
        this.photo = photo;
        this.passwordHash = passwordHash;
        this.status = status;
    }

    public UserDto(String lastName,
                   String firstName,
                   String middleName,
                   String email,
                   UserRole role,
                   UploadedFile photo,
                   String passwordHash,
                   UserStatus status) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.role = role;
        this.photo = photo;
        this.passwordHash = passwordHash;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public UploadedFile getPhoto() {
        return photo;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }
}
