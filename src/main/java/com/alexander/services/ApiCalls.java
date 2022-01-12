package com.alexander.services;

import com.alexander.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Class to call APIs
 */
@Slf4j
public class ApiCalls {

    private final RestTemplate restTemplate;

    public ApiCalls(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pokemon getPokemonInfo(String pokemonName) {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName;
            log.debug("using api url: {} ", url);

            return restTemplate.getForObject(url, Pokemon.class);
        }catch (final HttpClientErrorException e) {
            log.info("error retrieving pokemon, {}", e.getStatusCode());
        }
        return null;
    }
}
