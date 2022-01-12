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
    private Object formDescriptions;
    private Object habitat;
    private boolean isLegendary;

    @JsonProperty("form_descriptions")
    private void unpackNested(List<JSONObject> form_descriptions) {
        try {
            this.formDescriptions = form_descriptions.get(0).get("description");
        } catch (IndexOutOfBoundsException ie) {
            log.debug("empty description, ", ie);
            this.formDescriptions = null;
        }

    }

}
