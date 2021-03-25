package com.van.weatherApp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logException(ex);
        return new ResponseEntity<>("Unable to process the request", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(Exception ex) {
        logException(ex);
        return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<String> handleLimitExceededException(Exception ex) {
        logException(ex);
        return new ResponseEntity<>("Limit per hour for the APIKey exceeded. Please try again later", HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    private void logException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
    }


}
