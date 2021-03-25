package com.van.weatherApp.util;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@CacheConfig(cacheNames = "apiKeyTime")
@Component
public class ApiKeyCacheUtil {

    @Cacheable(value = "apiKeyTime", key = "#apiKey")
    public LocalDateTime getApiKeyBasedTime(String apiKey, LocalDateTime currentTime) {
        return currentTime;
    }
    @Cacheable(value = "apiKeyTime", key = "#apiKey")
    public LocalDateTime putApiKeyBasedTime(String apiKey, LocalDateTime currentTime) {
        return currentTime;
    }

    @CacheEvict(value = "apiKeyTime", key = "#apiKey")
    public void removeApiKeyBasedTime(String apiKey) {

    }
}
