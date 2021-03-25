package com.van.weatherApp.exception;

public class InvalidRequestException extends Exception {

    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }
}
