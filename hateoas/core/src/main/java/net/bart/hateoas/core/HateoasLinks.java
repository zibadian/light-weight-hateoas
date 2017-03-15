package net.bart.hateoas.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.providers.HateoasProviderHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public final class HateoasLinks {

    private final Map<String, URI> links = new HashMap<>();
    private volatile AbstractResourceLinkBuilder internalLink;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public final Map<String, String> getLinks() {
        if (links.isEmpty()) {
            return null;
        }
        final Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, URI> linkEntry : links.entrySet()) {
            if (linkEntry.getValue() != null) {
                result.put(linkEntry.getKey(), linkEntry.getValue().toString());
            }
        }
        return result;
    }

    public final void addLink(final String ref, final Object link) {
        if (internalLink != null) {
            final URI uri = internalLink.build();
            internalLink = null;
            addLink(ref, uri);
        } else if (link == null) {
            // Do nothing
        } else if (link instanceof URI) {
            links.put(ref, (URI) link);
        } else if (link instanceof LinkBuilder) {
            links.put(ref, ((LinkBuilder) link).build());
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
