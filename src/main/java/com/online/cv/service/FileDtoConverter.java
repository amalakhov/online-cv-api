package com.online.cv.service;

import com.online.cv.db.tables.pojos.Files;
import com.online.cv.domain.upload.UploadedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileDtoConverter {

    @Value("${online.cv.img.host.prefix}")
    private String imgPrefix;

    public UploadedFile convertImg(Files file) {
        return file == null ? null : new UploadedFile(file, imgPrefix);
    }
}
