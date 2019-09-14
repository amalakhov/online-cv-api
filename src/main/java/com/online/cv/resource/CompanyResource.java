package com.online.cv.resource;

import com.online.cv.db.tables.pojos.Company;
import com.online.cv.db.tables.pojos.Files;
import com.online.cv.domain.dto.CompanyDto;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.domain.upload.UploadedFile;
import com.online.cv.service.CompanyService;
import com.online.cv.service.FileDtoConverter;
import com.online.cv.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController
public class CompanyResource {
    private static final Logger logger = LoggerFactory.getLogger(CompanyResource.class);

    private final CompanyService companyService;
    private final FileStorageService storageService;
    private final FileDtoConverter converter;

    @Autowired
    public CompanyResource(CompanyService companyService,
                           FileStorageService storageService,
                           FileDtoConverter converter) {
        this.companyService = companyService;
        this.storageService = storageService;
        this.converter = converter;
    }

    @PostMapping(path = "/company", produces = "application/json")
    public ResponseEntity insert(@RequestBody Company company) {
        try {
            company = companyService.insert(company);
            UploadedFile photo;
            if (isNull(company.getPhotoId())) {
                photo = null;
            } else {
                final Files file = storageService.findById(company.getPhotoId());
                photo = converter.convertImg(file);
            }

            return ResponseEntity.ok(new CompanyDto(company.getId(), company.getName(), company.getDescription(), photo));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(ErrorCode.Common.COMMON_ERROR);
        }
    }

    @GetMapping(path = "/company/list", produces = "application/json")
    public List<CompanyDto> getAll() {
        try {
            final List<Company> companies = companyService.fetchAll();
            final Set<Integer> photosIds = companies.stream()
                                                 .map(Company::getPhotoId)
                                                 .collect(Collectors.toSet());

            final Map<Integer, Files> idToFile = storageService.findByIds(photosIds).stream()
                                                    .collect(Collectors.toMap(Files::getId, Function.identity()));

            return companies.stream()
                            .map(company -> {
                                UploadedFile photo;
                                if (isNull(company.getPhotoId()) || !idToFile.containsKey(company.getPhotoId())) {
                                    photo = null;
                                } else {
                                    photo = converter.convertImg(idToFile.get(company.getPhotoId()));
                                }

                                return new CompanyDto(company.getId(), company.getName(), company.getDescription(), photo);
                            })
                            .collect(Collectors.toList());

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }
}
