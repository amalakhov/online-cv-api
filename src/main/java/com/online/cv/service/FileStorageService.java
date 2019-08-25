package com.online.cv.service;

import com.online.cv.db.tables.daos.FilesDao;
import com.online.cv.db.tables.pojos.Files;
import com.online.cv.domain.common.UserDto;
import com.online.cv.domain.upload.UploadedFile;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final DateTimeFormatter DIR_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");

    private final FilesDao dao;
    private final FileDtoConverter fileDtoConverter;

    @Value("${online.cv.static.root.path}")
    private String rootPath;

    @Autowired
    public FileStorageService(Configuration configuration,
                              FileDtoConverter fileDtoConverter) {
        this.dao = new FilesDao(configuration);
        this.fileDtoConverter = fileDtoConverter;
    }

    public Files findById(Integer id) {
        return dao.fetchOneById(id);
    }

    public List<Files> findByIds(Collection<Integer> ids) {
        return dao.fetchById(ids.toArray(new Integer[0]));
    }

    public UploadedFile store(MultipartFile file, UserDto userDto) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String dir = LocalDate.now().format(DIR_FORMAT);
        String storedFilename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        storeFile(file, Paths.get(rootPath, dir, storedFilename));
        Files fileRecord = new Files(null, originalFilename, dir, storedFilename, null, userDto.getId());
        dao.insert(fileRecord);
        return fileDtoConverter.convertImg(fileRecord);
    }

    private void storeFile(MultipartFile file, Path path) throws IOException {
        Path folder = path.getParent();
        if (!java.nio.file.Files.exists(folder)) {
            folder.toFile().mkdirs();
        }
        file.transferTo(new File(path.toUri()));
    }
}
