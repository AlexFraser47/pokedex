package com.alexander;

import com.alexander.services.ApiCalls;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiCallsTest {
    private final RestTemplate restMock = mock(RestTemplate.class);
    private final ApiCalls underTest = new ApiCalls(restMock);
    private final Pokemon pokemon = new Pokemon();

    @Before
    public void setUp() {
        pokemon.setName("charmander");
        pokemon.setLegendary(false);
        pokemon.setHabitat("rare");
        pokemon.setDescription("this is a fire pokemon");
    }

    @Test
    public void getValidPokemonSpeciesApiCallTest() {

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);

        Pokemon result = underTest.getPokemonInfo("charmander");
        Assert.assertEquals("charmander", result.getName());
        Assert.assertFalse(result.isLegendary());
        Assert.assertEquals("rare", result.getHabitat());
        Assert.assertEquals("this is a fire pokemon", result.getDescription());
    }

    @Test
    public void pokemonSpeciesNotFoundApiCallTest() {
        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Assert.assertNull(underTest.getPokemonInfo("invalidPokemonName"));
    }

    @Test
    public void internalErrorApiCallTest() {
        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Assert.assertNull(underTest.getPokemonInfo("invalidPokemonName"));
    }

    @Test
    public void validTranslatorApiCallTest() {

        String translatorJsonResponse = "{\n" +
                "    \"success\": {\n" +
                "        \"total\": 1\n" +
                "    },\n" +
                "    \"contents\": {\n" +
                "        \"translated\": \"pokemon fire, it is\",\n" +
                "        \"text\": \"this is a fire pokemon\",\n" +
                "        \"translation\": \"yoda\"\n" +
                "    }\n" +
                "}";

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);
        when(restMock.postForObject(anyString(), any(), eq(String.class))).thenReturn(translatorJsonResponse);

        Pokemon result = underTest.getModifiedPokemonInfo("charmander");

        Assert.assertEquals("charmander", result.getName());
        Assert.assertFalse(result.isLegendary());
        Assert.assertEquals("rare", result.getHabitat());
        Assert.assertEquals("pokemon fire, it is", result.getDescription());
    }

    @Test
    public void descriptionDoesNotChangeWhenTranslatorApiLimitIsReachedTest() {

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);
        when(restMock.postForObject(anyString(), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.REQUEST_TIMEOUT));

        Pokemon result = underTest.getModifiedPokemonInfo("charmander");

        Assert.assertEquals("charmander", result.getName());
        Assert.assertFalse(result.isLegendary());
        Assert.assertEquals("rare", result.getHabitat());
        Assert.assertEquals("this is a fire pokemon", result.getDescription());
    }

    @Test
    public void descriptionDoesNotChangeWhenTranslatorApiCallIsRejectedTest() {

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);
        when(restMock.postForObject(anyString(), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Pokemon result = underTest.getModifiedPokemonInfo("charmander");

        Assert.assertEquals("charmander", result.getName());
        Assert.assertFalse(result.isLegendary());
        Assert.assertEquals("rare", result.getHabitat());
        Assert.assertEquals("this is a fire pokemon", result.getDescription());
    }

}
