package com.adepuu.montrackbackend.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class AuthRedisRepository {
    private static final String STRING_KEY_PREFIX = "montrack:jwt:strings:" ;
    private final ValueOperations<String, String> valueOps;

    public AuthRedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.valueOps = redisTemplate.opsForValue();
    }

    public void saveJwtKey(String email, String jwtKey) {
        valueOps.set(STRING_KEY_PREFIX+email, jwtKey, 1, TimeUnit.HOURS);
    }

    public String getJwtKey(String email) {
        return valueOps.get(STRING_KEY_PREFIX+email);
    }

    public void deleteJwtKey(String email) {
        valueOps.getOperations().delete(STRING_KEY_PREFIX+email);
    }
}