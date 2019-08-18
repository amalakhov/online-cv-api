package com.online.cv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class JsonMapperService {
    private static final Logger logger = LoggerFactory.getLogger(JsonMapperService.class);
    private final ObjectMapper mapper;

    public JsonMapperService() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> String toJSON(T entity) {
        try {
            checkNotNull(entity, "Entity is null.");
            return mapper.writeValueAsString(entity);
        } catch (Exception ex) {
            logger.error("Failed serialization entity to json", ex);
            throw new RuntimeException(ex);
        }
    }

    public <T> T fromJSON(String json, Class<T> type) {
        try {
            checkArgument(!Strings.isNullOrEmpty(json), "JSON is null or empty.");
            return mapper.readValue(json.getBytes(StandardCharsets.UTF_8), type);
        } catch (Exception ex) {
            logger.error("Failed deserialization from json", ex);
            throw new RuntimeException(ex);
        }
    }

    public <T> T fromJSON(String json, TypeReference type) {
        try {
            checkArgument(!Strings.isNullOrEmpty(json), "JSON is null or empty.");
            return mapper.readValue(json.getBytes(StandardCharsets.UTF_8), type);
        } catch (Exception ex) {
            logger.error("Failed deserialization from json", ex);
            throw new RuntimeException(ex);
        }
    }
}
