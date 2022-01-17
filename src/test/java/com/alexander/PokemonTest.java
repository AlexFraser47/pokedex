package com.alexander;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PokemonTest {

    @Test
    public void convertJsonIntoPokemonObject() throws Exception {
        // read json file
        String file = "src/test/resources/json.txt";
        String json = readFileAsString(file);

        Pokemon mappedPokemon = new ObjectMapper().readValue(json, Pokemon.class);

        Assert.assertEquals("charmander", mappedPokemon.getName());
        Assert.assertEquals("Obviously prefers hot places. When it rains, steam is said to spout from the tip of its tail.", mappedPokemon.getDescription());
        Assert.assertEquals("mountain", mappedPokemon.getHabitat());
        Assert.assertFalse(mappedPokemon.isLegendary());
    }

    private static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
