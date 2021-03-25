package com.van.weatherApp.exception;

public class LimitExceededException extends Exception {

    public LimitExceededException(String errorMessage) {
        super(errorMessage);
    }

}
