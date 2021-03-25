package junit.com.van.weatherApp.util;

import com.van.weatherApp.exception.InvalidRequestException;
import com.van.weatherApp.exception.LimitExceededException;
import com.van.weatherApp.util.APIKeyValidator;
import com.van.weatherApp.util.ApiKeyCacheUtil;
import com.van.weatherApp.util.CommonUtil;
import com.van.weatherApp.util.TokenBucketCacheUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class APIKeyValidatorTest {

    @InjectMocks
    private APIKeyValidator apiKeyValidator;

    @Mock
    private ApiKeyCacheUtil apiKeyCacheUtil;

    @Mock
    private TokenBucketCacheUtil tokenBucketCacheUtil;

    @Mock
    private CommonUtil commonUtil;

    @Test
    public void testInValidApiKey() {
        assertThrows(InvalidRequestException.class,
                () -> {
                    apiKeyValidator.validateApiKey("TST001");
                });
    }

    @Test
    public void testValidApiKey() throws Exception{
        LocalDateTime testTime = LocalDateTime.now();
        when(commonUtil.getCurrentDateTime()).thenReturn(testTime);
        when(apiKeyCacheUtil.getApiKeyBasedTime("WAPP-001", testTime)).thenReturn(testTime);
        apiKeyValidator.validateApiKey("WAPP-001");
    }

    @Test
    public void testKeyStartWindow() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        when(commonUtil.getCurrentDateTime()).thenReturn(testTime);
        when(apiKeyCacheUtil.getApiKeyBasedTime("WAPP-001", testTime)).thenReturn(testTime);
        apiKeyValidator.validateApiKey("WAPP-001");
        verify(tokenBucketCacheUtil, times(1)).resetApiKeyBasedLimit("WAPP-001");

    }

    @Test
    public void testKeyWindowExceeded() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        LocalDateTime mockCurrentTime = testTime.plusSeconds(3700);
        when(commonUtil.getCurrentDateTime()).thenReturn(mockCurrentTime);
        when(apiKeyCacheUtil.getApiKeyBasedTime(any(), any())).thenReturn(testTime);
        ReflectionTestUtils.setField(apiKeyValidator, "limitTime", 3600);
        apiKeyValidator.validateApiKey("WAPP-001");
        verify(tokenBucketCacheUtil, times(1)).resetApiKeyBasedLimit("WAPP-001");
        verify(apiKeyCacheUtil, times(1)).removeApiKeyBasedTime("WAPP-001");
        verify(apiKeyCacheUtil, times(1)).putApiKeyBasedTime("WAPP-001", mockCurrentTime);

    }

    @Test
    public void testKeyWithinWindowTokensExceed() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        LocalDateTime mockCurrentTime = LocalDateTime.now().plusSeconds(3000);
        when(commonUtil.getCurrentDateTime()).thenReturn(mockCurrentTime);
        when(apiKeyCacheUtil.getApiKeyBasedTime(any(), any())).thenReturn(testTime);
        when(tokenBucketCacheUtil.getExistingApiKeyBasedLimit("WAPP-001")).thenReturn(-1);
        apiKeyValidator.validateApiKey("WAPP-001");
        ReflectionTestUtils.setField(apiKeyValidator, "limitTime", 3600);

        assertThrows(LimitExceededException.class,
                () -> {
                    apiKeyValidator.validateApiKey("WAPP-001");
                });

    }

    @Test
    public void testKeyWithinWindow() throws Exception {
        LocalDateTime testTime = LocalDateTime.now();
        LocalDateTime mockCurrentTime = LocalDateTime.now().plusSeconds(3000);
        when(commonUtil.getCurrentDateTime()).thenReturn(mockCurrentTime);
        when(apiKeyCacheUtil.getApiKeyBasedTime(any(), any())).thenReturn(testTime);
        when(tokenBucketCacheUtil.getExistingApiKeyBasedLimit("WAPP-001")).thenReturn(3);
        apiKeyValidator.validateApiKey("WAPP-001");
        ReflectionTestUtils.setField(apiKeyValidator, "limitTime", 3600);
        apiKeyValidator.validateApiKey("WAPP-001");
    }
}
