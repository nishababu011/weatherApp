package junit.com.van.weatherApp.controller;

import com.van.weatherApp.controller.WeatherAppController;
import com.van.weatherApp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherAppControllerTest {

    @InjectMocks
    private WeatherAppController controller;

    @Mock
    private WeatherService weatherService;

    @Test
    public void testGetWeatherByPlace() throws Exception {
        when(weatherService.callWeatherService("TST001", "us", "UK")).thenReturn("Shiny bright");
        String weather = controller.getWeatherByPlace("TST001", "us", "UK");
        assertEquals(weather, "Shiny bright");
    }

}
