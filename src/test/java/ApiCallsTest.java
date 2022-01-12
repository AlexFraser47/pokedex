import com.alexander.Pokemon;
import com.alexander.services.ApiCalls;
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
        Object object = new Object();

        Pokemon pokemon = new Pokemon();
        pokemon.setName("charzard");
        pokemon.setLegendary(false);
        pokemon.setHabitat(object);
        pokemon.setFormDescriptions(object);

        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenReturn(pokemon);

        Pokemon result = underTest.getPokemonInfo("charzard");
        Assert.assertEquals("charzard", result.getName());
    }

    @Test
    public void getInvalidApiCallTest() {
        when(restMock.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Assert.assertNull(underTest.getPokemonInfo("invalidPokemonName"));
    }
}
