package com.online.cv.resource;

import com.online.cv.db.tables.pojos.Skill;
import com.online.cv.domain.common.SkillCategory;
import com.online.cv.domain.dto.SkillDto;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SkillResource {
    private static final Logger logger = LoggerFactory.getLogger(SkillResource.class);

    private final SkillService skillService;

    @Autowired
    public SkillResource(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping(path = "/skill", consumes = "application/json", produces = "application/json")
    public ResponseEntity insert(@RequestBody Skill skill) {
        try {
            skill = skillService.insert(skill);
            return ResponseEntity.ok(new SkillDto(skill.getId(), skill.getName(), SkillCategory.valueOf(skill.getCategory())));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(ErrorCode.Common.COMMON_ERROR);
        }
    }

    @GetMapping(path = "/skill/categories", produces = "application/json")
    public SkillCategory[] getSkillCategories() {
        try {
            return SkillCategory.values();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new SkillCategory[0];
        }
    }

    @GetMapping(path = "/skill/list", produces = "application/json")
    public List<SkillDto> getAll() {
        try {
            return toDto(skillService.fetchAll());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    @GetMapping(path = "/skill/category/{category}", produces = "application/json")
    public List<SkillDto> getByCategory(@PathVariable("category") String category) {
        try {
            return toDto(skillService.fetchByCategory(SkillCategory.valueOf(category)));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    @GetMapping(path = "/skill/per/category", produces = "application/json")
    public Map<String, List<SkillDto>> getPerCategory() {
        try {
            return skillService.fetchAllPerCategory().entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().name(), entry -> toDto(entry.getValue())));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new HashMap<>();
        }
    }

    private List<SkillDto> toDto(List<Skill> skills) {
        return skills.stream()
                     .map(skill -> new SkillDto(skill.getId(), skill.getName(), SkillCategory.valueOf(skill.getCategory())))
                     .collect(Collectors.toList());
    }
}
