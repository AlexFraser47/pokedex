package com.alexander;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class Pokemon {

    private String name;
    private String description;
    private String habitat;
    private boolean isLegendary;

    @JsonProperty("flavor_text_entries")
    private void unpackNestedDescription(List<JSONObject> description) {
        try {
            this.description = description.get(0).get("flavor_text").toString();
        } catch (IndexOutOfBoundsException ie) {
            log.debug("empty description, ", ie);
            this.description = null;
        }
    }

    @JsonProperty("habitat")
    private void unpackNestedHabitat(JSONObject habitat) {
        try {
            this.habitat = habitat.get("name").toString();
        } catch (NullPointerException e) {
            log.debug("empty habitat, ", e);
            this.habitat = null;
        }
    }

}
