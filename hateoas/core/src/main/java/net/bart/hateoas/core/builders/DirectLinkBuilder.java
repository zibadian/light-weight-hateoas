package net.bart.hateoas.core.builders;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DirectLinkBuilder extends AbstractLinkBuilder {

    private final URI uri;

    public DirectLinkBuilder(final String ref, final Object source) {
        super(ref);
        if (source == null) {
            throw new IllegalArgumentException("Hateoas link URI must not be null.");
        }
        if (source instanceof URI) {
            this.uri = (URI) source;
        } else if (source instanceof URL) {
            this.uri = internalParse((URL) source);
        } else {
            this.uri = internalParse(source.toString());
        }
    }

    private URI internalParse(final String source) {
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to parse link", e);
        }
    }

    private URI internalParse(final URL source) {
        try {
            return source.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to parse link", e);
        }
    }

    @Override
    public URI build() {
        return uri;
    }

}
