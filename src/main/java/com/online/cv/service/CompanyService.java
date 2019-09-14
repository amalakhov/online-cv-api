package com.online.cv.service;

import com.online.cv.db.tables.daos.CompanyDao;
import com.online.cv.db.tables.pojos.Company;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyDao companyDao;

    @Autowired
    public CompanyService(Configuration configuration) {
        this.companyDao = new CompanyDao(configuration);
    }

    public Company insert(Company company) {
        try {
            companyDao.insert(company);
            logger.info("insert::company = {}", company);
            return companyDao.fetchOneByName(company.getName());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Company> fetchAll() {
        try {
            return companyDao.findAll();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }
}
