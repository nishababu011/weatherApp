package com.van.weatherApp;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WeatherAppApplication.class})
@ActiveProfiles("test")
@TestPropertySource(
        properties = {"apikey.limitTime=5","apikey.maxTokens=5","apikey.endpoint=https://samples.openweathermap.org/data/2.5/weather"}
)
public @interface ComponentTest {
}
