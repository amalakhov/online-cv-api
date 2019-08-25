package com.online.cv.resource;

import com.online.cv.domain.common.UserDto;
import com.online.cv.domain.error.ErrorCode;
import com.online.cv.domain.upload.UploadedFile;
import com.online.cv.service.FileStorageService;
import com.online.cv.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FileStorageResource {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageResource.class);

    private final UserService userService;
    private final FileStorageService storage;

    @Autowired
    public FileStorageResource(UserService userService,
                               FileStorageService storage) {
        this.userService = userService;
        this.storage = storage;
    }

    @PostMapping(path = "/upload/img")
    public ResponseEntity addFile(@RequestParam("file") MultipartFile file,
                                  Principal principal) {
        try {
            final Optional<UserDto> optionalUser = userService.resolveUser(principal);
            final UserDto userDto = optionalUser.orElseThrow(() -> new RuntimeException("Can't resolve user"));
            return ResponseEntity.ok(storage.store(file, userDto));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(ErrorCode.FileUpload.UPLOAD_ERROR);
        }
    }

    @PostMapping(path = "/upload/imgs")
    public ResponseEntity addFiles(@RequestParam("files") MultipartFile[] files,
                                   Principal principal) {

        List<UploadedFile> result = new ArrayList<>();
        try {
            final Optional<UserDto> optionalUser = userService.resolveUser(principal);
            final UserDto userDto = optionalUser.orElseThrow(() -> new RuntimeException("Can't resolve user"));
            for (MultipartFile file : files) {
                UploadedFile storeResult = storage.store(file, userDto);
                result.add(storeResult);
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(ErrorCode.FileUpload.UPLOAD_ERROR);
        }
    }
}
