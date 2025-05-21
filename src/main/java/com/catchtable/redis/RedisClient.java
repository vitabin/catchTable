package com.catchtable.redis;

import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisClient {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T> void set(String key, T value) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue()
                         .set(key, json);
        } catch (JsonProcessingException e) {
            throw new FileException(FileErrorCode.SAVE_FAIL);
        }
    }

    public <T> void set(String key, T value, Duration timeout) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue()
                         .set(key, json, timeout);
        } catch (JsonProcessingException e) {
            throw new FileException(FileErrorCode.SAVE_FAIL);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        String json = redisTemplate.opsForValue()
                                   .get(key);
        try {
            return json == null ? null : objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new FileException(FileErrorCode.LOAD_FAIL);
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}

