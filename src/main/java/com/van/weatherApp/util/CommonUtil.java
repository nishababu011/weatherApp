package com.van.weatherApp.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonUtil {

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
