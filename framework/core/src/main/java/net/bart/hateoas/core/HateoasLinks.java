package net.bart.hateoas.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.bart.hateoas.core.builders.AbstractResourceLinkProvider;
import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.providers.HateoasProviderHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class HateoasLinks {

    private final Map<String, URI> links = new HashMap<>();
    private volatile AbstractResourceLinkProvider internalLink;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public final Map<String, String> getLinks() {
        final Map<String, String> result = links.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public final void addLink(final String ref, final Object link) {
        if (internalLink != null) {
            final String uri = internalLink.build();
            internalLink = null;
            addLink(ref, uri);
        } else if (link == null) {
            // Do nothing
        } else if (link instanceof URI) {
            links.put(ref, (URI) link);
        } else if (link instanceof LinkBuilder) {
            addLink(ref, ((LinkBuilder) link).build());
        } else {
            try {
                links.put(ref, new URI(link.toString()));
            } catch (URISyntaxException e) {
                throw new HateoasException("Failed to create uri: " + link, e);
            }
        }
    }

    public final <T> T resource(final Class<T> resourceClass) {
        internalLink = HateoasProviderHelper.getResourceLinkBuilder();
        return internalLink.getResource(resourceClass);
    }

}
