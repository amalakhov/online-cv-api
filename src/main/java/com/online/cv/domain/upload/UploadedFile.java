package com.online.cv.domain.upload;

import com.online.cv.db.tables.pojos.Files;

public class UploadedFile {
    private static final String URL_DELIMITER = "/";

    private Integer id;
    private String uri;
    @Deprecated
    private String url;

    public UploadedFile() {
        this.id = null;
        this.uri = null;
        this.url = null;
    }

    public UploadedFile(Files files, String hostPrefix) {
        this.id = files.getId();
        this.uri = files.getDir() + URL_DELIMITER + files.getStoredFilename();
        this.url = hostPrefix + uri;
    }

    public Integer getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }
}
