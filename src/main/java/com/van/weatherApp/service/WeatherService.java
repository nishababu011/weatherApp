package com.van.weatherApp.service;

import com.van.weatherApp.domain.Weather;
import com.van.weatherApp.dto.WeatherDto;
import com.van.weatherApp.repository.WeatherAppRespository;
import com.van.weatherApp.repository.WeatherDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherService {

    @Autowired
    private APIKeyService apiKeyService;

    @Autowired
    private WeatherAppRespository appRespository;

    @Autowired
    private WeatherDataRepository dataRepository;

    /**
     *
     * @param apiKey
     * @param city
     * @param country
     * @return
     * @throws Exception
     */
    public String callWeatherService(String apiKey, String city, String country) throws Exception {
        log.info("Entering the WeatherService for the city and country -> {}, {}", city, country);
        apiKeyService.verifyAPILimitExceeded(apiKey);
        String description = null;
        // if ApiKey is valid query the DB for the search criteria
        Weather weather = dataRepository.findByCityAndCountry(city, country);
         if(weather != null) {
             log.info("Retrieved weather data from database for city and country -> {} , {}", city, country);
             description = weather.getDescription();
         } else {
             WeatherDto weatherDto = appRespository.fetchWeatherData(apiKey, city, country);
             if (weatherDto != null && weatherDto.getWeather() != null && weatherDto.getWeather().size() > 0) {
                 description = weatherDto.getWeather().get(0).getDescription();
                 saveWeatherStats(city, country, description);
             } else {
                 description = "No data available.";
             }

         }
        apiKeyService.reduceTokenCount(apiKey); // reduce the retrieval count
        return description;
    }

    /**
     *
     * @param city
     * @param country
     * @param description
     */
    public void saveWeatherStats(String city, String country, String description) {
        Weather weather = Weather.builder()
                .city(city)
                .country(country)
                .description(description)
                .build();
        dataRepository.save(weather);
    }
}
