import com.alexander.Pokemon;
import com.alexander.services.ApiCalls;
import org.json.simple.JSONObject;
import org.junit.Assert;
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

    @Test
    public void getValidApiCallTest() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("charzard");
        pokemon.setLegendary(false);
        pokemon.setHabitat("rare");
        pokemon.setDescription("fire pokemon");

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);

        Pokemon result = underTest.getPokemonInfo("charzard");
        Assert.assertEquals("charzard", result.getName());
        Assert.assertFalse(result.isLegendary());
        Assert.assertEquals("rare", result.getHabitat());
        Assert.assertEquals("fire pokemon", result.getDescription());
    }

    @Test
    public void pokemonNotFoundApiCallTest() {
        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Assert.assertNull(underTest.getPokemonInfo("invalidPokemonName"));
    }

    @Test
    public void internalErrorApiCallTest() {
        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Assert.assertNull(underTest.getPokemonInfo("invalidPokemonName"));
    }
}
