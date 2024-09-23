package com.msa.user_service.service;

import static com.msa.user_service.constant.JWT_SET.REFRESH_TOKEN_EXPIRATION;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshToken(String token, String userId) {
        redisTemplate.opsForValue()
            .set(token, userId, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    public String getUserIdFromToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }

    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }

    public boolean isTokenExists(String token) {
        return redisTemplate.hasKey(token);
    }
}
