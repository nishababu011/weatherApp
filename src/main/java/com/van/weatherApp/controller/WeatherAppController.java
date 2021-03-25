package com.van.weatherApp.controller;

import com.van.weatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherAppController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    @ResponseBody
    public String getWeatherByPlace(@RequestHeader String apiKey,
                                                 @RequestParam String city, @RequestParam String country) throws Exception {
        return weatherService.callWeatherService(apiKey,city, country);
    }
}
