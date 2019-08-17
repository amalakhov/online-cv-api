package com.online.cv.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "file:/etc/online-cv/db.properties")
public class DBProperties {

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Value("${db.name}")
    private String dbName;

    @Value("${db.url}")
    private String url;


    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUrl() {
        return url;
    }
}
