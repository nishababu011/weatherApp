package com.van.weatherApp.service;

import com.van.weatherApp.util.APIKeyValidator;
import com.van.weatherApp.util.TokenBucketCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class APIKeyService {

    @Autowired
    private APIKeyValidator apiKeyValidator;

    @Autowired
    private TokenBucketCacheUtil tokenBucketCacheUtil;

    public void verifyAPILimitExceeded(String apiKey) throws Exception {
        apiKeyValidator.validateApiKey(apiKey);

    }

    public void reduceTokenCount(String apiKey) {
        int currentCount =  tokenBucketCacheUtil.getExistingApiKeyBasedLimit(apiKey);
        tokenBucketCacheUtil.putApiKeyBasedLimit(apiKey, currentCount);
    }

}
