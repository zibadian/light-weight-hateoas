package net.bart.hateoas.core.builders.urls;

import net.bart.hateoas.core.builders.UrlPart;

public class UrlPathPart implements UrlPart {

    private final boolean mustPrefixWithSlash;
    private String href;

    public UrlPathPart(final String href) {
        this(href, true);
    }

    public UrlPathPart(final String href, final boolean mustPrefixWithSlash) {
        this.href = href;
        this.mustPrefixWithSlash = mustPrefixWithSlash;
    }

    @Override
    public String getHref() {
        final String result = href;
        if (result == null || !mustPrefixWithSlash || result.isEmpty() || result.charAt(0) == '/') {
            return result;
        }
        return '/' + result;
    }

}
