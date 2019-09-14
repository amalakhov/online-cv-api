package com.online.cv.domain.dto;

import com.online.cv.domain.common.SkillCategory;

public class SkillDto {
    private Integer id;
    private String  name;
    private SkillCategory category;

    public SkillDto(Integer id, String name, SkillCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SkillCategory getCategory() {
        return category;
    }
}
