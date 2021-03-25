package com.van.weatherApp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@CacheConfig(cacheNames = "apiKeyTokenBucket")
@Component
public class TokenBucketCacheUtil {

    @Value(value = "${apikey.maxTokens}")
    private int maxTokens;

    @Cacheable(value = "apiKeyTokenBucket", key = "#apiKey")
    public int getExistingApiKeyBasedLimit(String apiKey) {
        return maxTokens;
    }

    @CachePut(value = "apiKeyTokenBucket", key = "#apiKey")
    public int putApiKeyBasedLimit(String apiKey, int count) {
        return --count;
    }

    @CacheEvict(value = "apiKeyTokenBucket", key = "#apiKey")
    public void resetApiKeyBasedLimit(String apiKey) {
    }

}
