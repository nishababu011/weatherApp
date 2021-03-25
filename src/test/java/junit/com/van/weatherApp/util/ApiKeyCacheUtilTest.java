package junit.com.van.weatherApp.util;

import com.van.weatherApp.util.ApiKeyCacheUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ApiKeyCacheUtilTest {

    @InjectMocks
    private ApiKeyCacheUtil apiKeyCacheUtil;

    @Test
    public void testGetApiKeyBasedTime() {
        LocalDateTime oldTime = LocalDateTime.now().minusSeconds(300);
        assertEquals(oldTime, apiKeyCacheUtil.getApiKeyBasedTime("TST-001", oldTime));
    }

    @Test
    public void testPutApiKeyBasedTime() {
        LocalDateTime oldTime = LocalDateTime.now().minusSeconds(300);
        assertEquals(oldTime, apiKeyCacheUtil.putApiKeyBasedTime("TST-001", oldTime));
    }


}
