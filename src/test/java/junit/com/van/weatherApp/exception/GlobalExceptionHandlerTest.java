package junit.com.van.weatherApp.exception;

import com.van.weatherApp.exception.GlobalExceptionHandler;
import com.van.weatherApp.exception.InvalidRequestException;
import com.van.weatherApp.exception.LimitExceededException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.management.RuntimeMBeanException;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private Exception exception;

    @Test
    public void testhandleException() {
        ResponseEntity response = globalExceptionHandler.handleException(exception);
        assertEquals(response.getBody(), "Unable to process the request");
        assertEquals(response.getStatusCode().toString(), "500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void testhandleInvalidRequestException() {
        ResponseEntity response = globalExceptionHandler.handleInvalidRequestException(
                new InvalidRequestException("error"));
        assertEquals(response.getBody(), "Invalid Request");
        assertEquals(response.getStatusCode().toString(), "400 BAD_REQUEST");
    }

    @Test
    public void testhandleLimitExceededException() {
        ResponseEntity response = globalExceptionHandler.handleLimitExceededException(
                new LimitExceededException("error"));
        assertEquals(response.getBody(), "Limit per hour for the APIKey exceeded. Please try again later");
        assertEquals(response.getStatusCode().toString(), "509 BANDWIDTH_LIMIT_EXCEEDED");
    }



}
