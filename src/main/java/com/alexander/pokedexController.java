package com.alexander;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class pokedexController {

    @GetMapping("/pokemon/{pokemon}")
    public String search(@PathVariable String pokemon){
        return String.format("Pokemon info on %s...", pokemon);
    }
}
