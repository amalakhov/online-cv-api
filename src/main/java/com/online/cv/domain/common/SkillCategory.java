package com.online.cv.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SkillCategory {
    BACKEND("Backend"),
    FRONTEND("Frontend"),
    DEVOPS("DevOps"),
    IDE("Integrated development environment (IDE)"),
    VC("Version control");

    private String id;
    private String title;

    SkillCategory(String title) {
        this.id = this.name();
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
