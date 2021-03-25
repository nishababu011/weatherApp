package junit.com.van.weatherApp.util;

import com.van.weatherApp.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CommonUtilTest {

    @InjectMocks
    private CommonUtil commonUtil;

    @Test
    public void testGetCurrentDateTime() {
        assertNotNull(commonUtil.getCurrentDateTime());
    }
}
