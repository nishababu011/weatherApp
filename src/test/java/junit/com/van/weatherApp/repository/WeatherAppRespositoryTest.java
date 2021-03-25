package junit.com.van.weatherApp.repository;

import com.van.weatherApp.dto.Details;
import com.van.weatherApp.dto.WeatherDto;
import com.van.weatherApp.repository.WeatherAppRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherAppRespositoryTest {

    @InjectMocks
    private WeatherAppRespository respository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testFetchWeatherData() throws Exception {
        ReflectionTestUtils.setField(respository, "endPoint", "http://test-url");
        ResponseEntity mockResponse = mock(ResponseEntity.class);
        HttpEntity<String> entity = mock(HttpEntity.class);
        String url = "http://test-url?appid=TST001&q=UK,us";
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(mockResponse);

        when(mockResponse.getBody()).thenReturn(WeatherDto.builder()
                .weather(Arrays.asList(Details.builder().description("Shiny bright").build())).build());
        WeatherDto result = respository.fetchWeatherData("TST001", "us", "UK");
        assertEquals(result.getWeather().get(0).getDescription(), "Shiny bright");

    }

}
