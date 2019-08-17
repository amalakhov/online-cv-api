package com.online.cv.context;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
public class DaoConfiguration {

    @Autowired
    private DBProperties dbProperties;

    @Bean
    public HikariDataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername(dbProperties.getUser());
        config.setPassword(dbProperties.getPassword());
        config.setJdbcUrl(dbProperties.getUrl() + dbProperties.getDbName());
        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        config.setPoolName("online-cv-cp");
        config.addDataSourceProperty("ApplicationName", "online-cv-api");
        config.setMaximumPoolSize(45);
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(100));
        config.setMaxLifetime(TimeUnit.SECONDS.toMillis(60));

        return config;
    }

    @Bean
    public org.jooq.Configuration configuration() {
        return new DefaultConfiguration().set(dataSource()).set(SQLDialect.POSTGRES);
    }
}
