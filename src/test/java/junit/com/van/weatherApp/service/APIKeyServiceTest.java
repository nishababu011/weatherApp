package junit.com.van.weatherApp.service;

import com.van.weatherApp.service.APIKeyService;
import com.van.weatherApp.util.APIKeyValidator;
import com.van.weatherApp.util.CommonUtil;
import com.van.weatherApp.util.TokenBucketCacheUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class APIKeyServiceTest {

    @InjectMocks
    private APIKeyService apiKeyService;

    @Mock
    private TokenBucketCacheUtil tokenBucketCacheUtil;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private APIKeyValidator apiKeyValidator;

    @Test
    public void testVerifyAPILimitExceeded() throws Exception {
        apiKeyService.verifyAPILimitExceeded("TST001");
    }

    @Test
    public void testReduceTokenCount() throws Exception {
        when(tokenBucketCacheUtil.getExistingApiKeyBasedLimit("TST001")).thenReturn(1);
        apiKeyService.reduceTokenCount("TST001");
    }

    @Test
    public void testReduceTokenCountReturn() throws Exception {
        when(tokenBucketCacheUtil.getExistingApiKeyBasedLimit("TST001")).thenReturn(0);
        apiKeyService.reduceTokenCount("TST001");
    }

}
