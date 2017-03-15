package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LinkBuilder {

    private final List<UrlPart> urlParts = new ArrayList<>();

    public URI build() {
        try {
            return new URI(urlParts
                    .stream()
                    .sorted((e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder()))
                    .map(UrlPart::getHref)
                    .collect(Collectors.joining()));
        } catch (URISyntaxException e) {
            throw new HateoasException(e);
        }
    }

    public LinkBuilder addPart(final UrlPart part) {
        if (part.isUnique()) {
            addUniquePart(part);
        } else {
            addNonUniquePart(part);
        }
        return this;
    }

    private void addNonUniquePart(final UrlPart part) {
        urlParts.add(part);
    }

    private void addUniquePart(final UrlPart part) {
         urlParts.removeIf(i -> i.getClass().equals(part.getClass()));
        addNonUniquePart(part);
    }

}
