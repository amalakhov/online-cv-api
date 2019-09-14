package com.online.cv.service;

import com.online.cv.db.tables.daos.SkillDao;
import com.online.cv.db.tables.pojos.Skill;
import com.online.cv.domain.common.SkillCategory;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillService {
    private static final Logger logger = LoggerFactory.getLogger(SkillService.class);

    private final SkillDao dao;

    @Autowired
    public SkillService(Configuration configuration) {
        this.dao = new SkillDao(configuration);
    }

    public Skill insert(Skill skill) {
        try {
            dao.insert(skill);
            logger.info("insert::skill = {}", skill);
            return dao.fetchOneByName(skill.getName());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    public Map<SkillCategory, List<Skill>> fetchAllPerCategory() {
        try {
            return dao.findAll().stream()
                    .collect(Collectors.groupingBy(s -> SkillCategory.valueOf(s.getCategory())));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new HashMap<>();
        }
    }

    public List<Skill> fetchByCategory(SkillCategory category) {
        try {
            return dao.fetchByCategory(category.name());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    public List<Skill> fetchAll() {
        try {
            return dao.findAll();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }
}
