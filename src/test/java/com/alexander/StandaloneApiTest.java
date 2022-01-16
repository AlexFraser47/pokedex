package com.alexander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * Run MainApplicationClass.
 * To test the api, you can either use a browser or simply run this main class.
 * Change the pokemon string value or task to test different endpoints.
 */
@Slf4j
public class StandaloneApiTest {
    private static String task1 = "http://localhost:6001/pokemon/";
    private static String task2 = "http://localhost:6001/pokemon/translated/";
    private static String pokemon = "mewtwo";


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForEntity(task1 + pokemon, String.class).getBody();

        log.info(response);
    }

}
