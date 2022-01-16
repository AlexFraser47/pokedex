package com.alexander.controllers;

import com.alexander.Pokemon;
import com.alexander.services.ApiCalls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Controller class where GET requests are mapped.
 */
@RestController
@Slf4j
public class PokedexController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiCalls apiCalls = new ApiCalls(restTemplate);

    @GetMapping("/pokemon/{pokemon}")
    public Pokemon search(@PathVariable String pokemon) {
        log.info("searching for pokemon: " + pokemon);

        return apiCalls.getPokemonInfo(pokemon.toLowerCase());
    }

    @GetMapping("/pokemon/translated/{pokemonName}")
    public Pokemon searchModifiedPokemon(@PathVariable String pokemonName) {
        log.info("searching for pokemon: " + pokemonName);

        return apiCalls.getModifiedPokemonInfo(pokemonName.toLowerCase());
    }

}
