package com.van.weatherApp.util;

import com.van.weatherApp.common.Constants;
import com.van.weatherApp.exception.InvalidRequestException;
import com.van.weatherApp.exception.LimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class APIKeyValidator {

    @Autowired
    private ApiKeyCacheUtil apiKeyCacheUtil;

    @Autowired
    private TokenBucketCacheUtil tokenBucketCacheUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Value(value = "${apikey.limitTime:3600}")
    private long limitTime;


    /**
     * This method validates the APIKey and resets the cache values.
     * @param apiKey
     * @throws Exception
     */
    public void validateApiKey(String apiKey) throws Exception {
        if (!Constants.API_KEYS.contains(apiKey)) {
            throw new InvalidRequestException(Constants.ERROR_INVALID_API_KEY);
        }
        resetAndValidateTokens(apiKey);
    }

    /**
     * This method checks the limit time windows and keeps track of the available tokens.
     * @param apiKey
     */
    private void resetAndValidateTokens(String apiKey) throws Exception{
        LocalDateTime currentTime = commonUtil.getCurrentDateTime();
        LocalDateTime cachedApiKeyTime = apiKeyCacheUtil.getApiKeyBasedTime(apiKey, currentTime);
        if (currentTime.equals(cachedApiKeyTime)) {
            log.info("Limit time window started for APIKey > {}" , apiKey);
            tokenBucketCacheUtil.resetApiKeyBasedLimit(apiKey);
        } else if (Duration.between(cachedApiKeyTime, currentTime).getSeconds() > limitTime) {
            log.info("Limit time window resetting for APIKey > {}" , apiKey);
            apiKeyCacheUtil.removeApiKeyBasedTime(apiKey);
            tokenBucketCacheUtil.resetApiKeyBasedLimit(apiKey);
            apiKeyCacheUtil.putApiKeyBasedTime(apiKey, currentTime);
        } else {
            log.info("APIKey {} Within the Limit time window, checking for remaining tokens" , apiKey);
            int currentToken = tokenBucketCacheUtil.getExistingApiKeyBasedLimit(apiKey);
            if (currentToken <= 0) {
                throw new LimitExceededException(Constants.ERROR_LIMIT_EXCEEDED);
            }

        }
    }
}
