package net.bart.hateoas.core.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkBuilder {

    private final List<UrlPart> urlParts = new ArrayList<>();
    private boolean hasErrors = false;

    public final boolean hasErrors() {
        return hasErrors;
    }

    protected final void setErrorsDetected(final boolean hasErrors) {
        if (!this.hasErrors) {
            this.hasErrors = hasErrors;
        }
    }

    public String build() {
        if (hasErrors) {
            return null;
        }
        return urlParts
                .stream()
                .map(UrlPart::getHref)
                .collect(Collectors.joining());
    }

    public final LinkBuilder addPart(final UrlPart part) {
        urlParts.add(part);
        return this;
    }

}
