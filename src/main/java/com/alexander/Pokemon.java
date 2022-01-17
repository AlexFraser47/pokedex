package com.alexander;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

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
    private void unpackNestedDescription(List<JSONObject> descriptions) {
        this.description = null;

        for (JSONObject description : descriptions) {
            Map<String, String> getDescriptions = (Map<String, String>) description.get("language");
            String language = getDescriptions.get("name");
            if ("en".equalsIgnoreCase(language)) {
                this.description = description.get("flavor_text").toString().replace("\n", " ")
                        .replace("\f", " ").replace("é", "e");
                break;
            }
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

    @JsonProperty("is_legendary")
    private void setLegendaryStatus(boolean isLegendary) {
        this.isLegendary = isLegendary;
    }

    @JsonProperty(value = "isLegendary")
    public boolean isLegendary() {
        return isLegendary;
    }

}
