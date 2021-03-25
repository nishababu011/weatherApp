package com.van.weatherApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentTest
@AutoConfigureMockMvc
public class WeatherAppApplicationTest {

    private static final String BASE_URL = "http://localhost:8090";

    private static final String API_PATH = "/weather";

    @Autowired
    private MockMvc mvc;

    @Test
    void test_call_weatherservice() throws Exception {
        String url = "/weather?city=London&country=uk";
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey","WAPP-002")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void test_call_weatherservice_missing_city() throws Exception {
        String url = "/weather?country=uk";
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey","WAPP-002")).andExpect(status().is4xxClientError());
    }

    @Test
    void test_call_weatherservice_missing_cntry() throws Exception {
        String url = "/weather?city=London";
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey","WAPP-002")).andExpect(status().is4xxClientError());
    }

    @Test
    void test_call_weatherservice_missing_api_key() throws Exception {
        String url = "/weather?city=London&country=uk";
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test_call_weatherservice_invalid_api_key() throws Exception {
        String url = "/weather?city=London&country=uk";
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey","TEST-002")).andExpect(status().is4xxClientError());
    }

    @Test
    void test_call_weatherservice_multiple_times() throws Exception {
        String url = "/weather?city=London&country=uk";
        int times = 5;
        while(times > 0 ) {
            mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                    .header("apiKey", "WAPP-001")).andExpect(status().is2xxSuccessful());
            times--;
        }
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey", "WAPP-001")).andExpect(status().is5xxServerError());
    }

    @Test
    void test_call_weather_multiple_times_after_limit() throws Exception {
        String url = "/weather?city=London&country=uk";
        int times = 5;
        while(times > 0 ) {
            mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                    .header("apiKey", "WAPP-003")).andExpect(status().is2xxSuccessful());
            times--;
        }
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey", "WAPP-003")).andExpect(status().is5xxServerError());
        Thread.sleep(5000);
        mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
                .header("apiKey", "WAPP-003")).andExpect(status().is2xxSuccessful());
    }


}
