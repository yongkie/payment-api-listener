package com.alphabet.simple.payment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public <T> T convertJson(String json, Class<T> type) {
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(json, type);
        } catch (IOException ex) {
            log.debug("Failed to convert JSON string to object", ex);
        }
        return null;
    }

    public String toJsonString(Object object) {
        try {
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.debug("Failed to convert object to JSON string", ex);
        }
        return null;
    }
}
