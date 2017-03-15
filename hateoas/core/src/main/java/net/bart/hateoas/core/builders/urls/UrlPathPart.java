package net.bart.hateoas.core.builders.urls;

public class UrlPathPart extends AbstractUrlPart {

    private final boolean mustPrefixWithSlash;

    public UrlPathPart(final String href) {
        this(href, true);
    }

    public UrlPathPart(final String href, final boolean mustPrefixWithSlash) {
        super(href);
        this.mustPrefixWithSlash = mustPrefixWithSlash;
    }

    @Override
    public String getHref() {
        final String result = super.getHref();
        if (result == null || !mustPrefixWithSlash || result.isEmpty() || result.charAt(0) == '/') {
            return result;
        }
        return '/' + result;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public int getOrder() {
        return 5;
    }

}
