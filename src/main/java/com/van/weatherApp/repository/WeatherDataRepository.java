package com.van.weatherApp.repository;

import com.van.weatherApp.domain.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends CrudRepository<Weather, Integer> {

    Weather findByCityAndCountry(String city, String country);

}
