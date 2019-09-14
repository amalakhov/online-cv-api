package com.online.cv.domain.dto;

import com.online.cv.domain.upload.UploadedFile;

public class CompanyDto {
    private Integer id;
    private String  name;
    private String  description;
    private UploadedFile photo;

    public CompanyDto(Integer id, String name, String description, UploadedFile photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UploadedFile getPhoto() {
        return photo;
    }
}
