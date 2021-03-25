package junit.com.van.weatherApp.util;

import com.van.weatherApp.util.TokenBucketCacheUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TokenBucketCacheUtilTest {

    @InjectMocks
    private TokenBucketCacheUtil tokenBucketCacheUtil;

    @Test
    public void testGetExistingApiKeyBasedLimit() {
        ReflectionTestUtils.setField(tokenBucketCacheUtil, "maxTokens", 5);
        assertEquals(5, tokenBucketCacheUtil.getExistingApiKeyBasedLimit("TST001"));

    }

    @Test
    public void testPutApiKeyBasedLimit() {
        assertEquals(3, tokenBucketCacheUtil.putApiKeyBasedLimit("TST001", 4));

    }
}
