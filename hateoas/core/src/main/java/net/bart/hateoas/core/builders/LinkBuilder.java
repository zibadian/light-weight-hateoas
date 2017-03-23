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
                .sorted((e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder()))
                .map(UrlPart::getHref)
                .collect(Collectors.joining());
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
