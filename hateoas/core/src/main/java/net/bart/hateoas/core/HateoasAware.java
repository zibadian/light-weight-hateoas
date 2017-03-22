package net.bart.hateoas.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public interface HateoasAware {

    String HATEOAS_CONTEXT_PROPERTY = "__Hateoas";

    @JsonProperty
    @JsonUnwrapped
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    HateoasLinks getLinks();

    @JsonIgnore
    default HateoasAware addLink(final String ref, final Object link) {
        getLinks().addLink(ref, link);
        return this;
    }

}
