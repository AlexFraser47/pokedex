package com.alexander.services;

import com.alexander.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class to call APIs
 */
@Slf4j
public class ApiCalls {

    private final RestTemplate restTemplate;
    private final static String POKE_URL = "https://pokeapi.co/api/v2/pokemon-species/";
    private final static String YODA_URL = "https://api.funtranslations.com/translate/yoda.json";
    private final static String SHAKESPEARE_URL = "https://api.funtranslations.com/translate/shakespeare.json";

    public ApiCalls(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Pokemon getPokemonInfo(String pokemonName) {
        try {
            String url = POKE_URL + pokemonName;
            log.info("using api url: {} ", url);

            return restTemplate.getForObject(url, Pokemon.class);
        }catch (final HttpClientErrorException e) {
            log.info("error retrieving pokemon, {}", e.getStatusCode());
        }
        return null;
    }

    public Pokemon getModifiedPokemonInfo(String pokemonName) {
        Pokemon modifiedPokemon = null;
        try {
            String url = POKE_URL+ pokemonName;
            log.info("using api url: {} ", url);

            modifiedPokemon = restTemplate.getForObject(url, Pokemon.class);

            if ("cave".equalsIgnoreCase(modifiedPokemon.getHabitat()) || modifiedPokemon.isLegendary()) {
                log.info("using api url: {} ", YODA_URL);

                String translatedDescription = getTranslatedDescription(modifiedPokemon, YODA_URL);
                modifiedPokemon.setDescription(translatedDescription);
            }
            else {
                log.info("using api url: {} ", SHAKESPEARE_URL);
                String translatedDescription = getTranslatedDescription(modifiedPokemon, SHAKESPEARE_URL);
                modifiedPokemon.setDescription(translatedDescription);
            }
            return modifiedPokemon;
        }catch (final HttpClientErrorException e) {
            log.info("error {}", e.getStatusCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return modifiedPokemon;
    }

    private String getTranslatedDescription(Pokemon modifiedPokemon, String url) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", URLEncoder.encode(modifiedPokemon.getDescription(), StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);

        String translationResponse = restTemplate.postForObject(url, entity, String.class);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(translationResponse);
        JSONObject jsonObject1 = (JSONObject) json.get("contents");

        return URLDecoder.decode(jsonObject1.get("translated").toString(), StandardCharsets.UTF_8);
    }
}
