package net.bart.hateoas.core.builders.urls;

public class BaseUrlPart {

    private String href;
    private final boolean mustPrefixWithSlash;

    public BaseUrlPart(final String href, final boolean mustPrefixWithSlash) {
        this.href = href;
        this.mustPrefixWithSlash = mustPrefixWithSlash;
    }

    public String getHref() {
        return href;
    }

    protected final BaseUrlPart setHref(final String href) {
        this.href = href;
        return this;
    }

    @Override
    public String toString() {
        final String result = getHref();
        if (!mustPrefixWithSlash || result.charAt(0) == '/') {
            return result;
        }
        return '/' + result;
    }

}
