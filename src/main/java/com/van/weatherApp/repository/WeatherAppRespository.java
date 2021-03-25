package com.van.weatherApp.repository;

import com.van.weatherApp.dto.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Repository
@Slf4j
public class WeatherAppRespository {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${apikey.endpoint}")
    private String endPoint;

    public WeatherDto fetchWeatherData(String apiKey, String city, String country) {
        log.info("Fetching weather data from Weather app for city and country -> {} , {}", city, country);
        StringBuilder url = new StringBuilder(endPoint).append("?appid=").append(apiKey);
        if (!Strings.isBlank(city) && !Strings.isBlank(country)) {
            url.append("&q=").append(country).append(",").append(city);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        WeatherDto weatherDto = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, WeatherDto.class).getBody();
        return weatherDto;

    }
}
