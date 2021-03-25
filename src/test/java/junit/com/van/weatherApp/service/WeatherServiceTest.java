package junit.com.van.weatherApp.service;

import com.van.weatherApp.domain.Weather;
import com.van.weatherApp.dto.Details;
import com.van.weatherApp.dto.WeatherDto;
import com.van.weatherApp.exception.InvalidRequestException;
import com.van.weatherApp.exception.LimitExceededException;
import com.van.weatherApp.repository.WeatherAppRespository;
import com.van.weatherApp.repository.WeatherDataRepository;
import com.van.weatherApp.service.APIKeyService;
import com.van.weatherApp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private APIKeyService apiKeyService;

    @Mock
    private WeatherAppRespository appRespository;

    @Mock
    private WeatherDataRepository dataRepository;

    @Test
    public void testCallWeatherServiceDatainDB() throws Exception {
        when(dataRepository.findByCityAndCountry("uk", "London")).thenReturn(
                Weather.builder().description("Test weather").build());
        String description = weatherService.callWeatherService("TST001", "uk", "London");
        assertEquals("Test weather", description);

    }

    @Test
    public void testCallWeatherServiceDataNotPresent() throws Exception {
        when(dataRepository.findByCityAndCountry("uk", "London")).thenReturn(null);
        when(appRespository.fetchWeatherData("TST001","uk", "London"))
                .thenReturn(WeatherDto.builder().weather(Arrays.asList(Details.builder().description("Test weather").build())).build());
        String description = weatherService.callWeatherService("TST001", "uk", "London");
        assertEquals("Test weather", description);

    }

    @Test
    public void testCallWeatherServiceDataNotPresentEmpty() throws Exception {
        when(dataRepository.findByCityAndCountry("uk", "London")).thenReturn(null);
        when(appRespository.fetchWeatherData("TST001","uk", "London"))
                .thenReturn(WeatherDto.builder().weather(Arrays.asList()).build());
        String description = weatherService.callWeatherService("TST001", "uk", "London");
        assertEquals("No data available.", description);

    }

    @Test
    public void testCallWeatherServiceLimitException() throws Exception {
        doThrow(new LimitExceededException("Limit Exceeded")).when(apiKeyService).verifyAPILimitExceeded("TST001");
        assertThrows(LimitExceededException.class,
                () -> {
                    weatherService.callWeatherService("TST001", "uk", "London");
                });

    }

    @Test
    public void testCallWeatherServiceInvalidReException() throws Exception {
        doThrow(new InvalidRequestException("Invalid Request")).when(apiKeyService).verifyAPILimitExceeded("TST001");
        assertThrows(InvalidRequestException.class,
                () -> {
                    weatherService.callWeatherService("TST001", "uk", "London");
                });

    }
}
