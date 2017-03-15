package net.bart.hateoas.spring.builder;

import java.lang.reflect.Parameter;

public class AbstractUrlPart {
    private String href;
    private final boolean mustPrefixWithSlash;

    public AbstractUrlPart(final String href, final boolean mustPrefixWithSlash) {
        this.href = href;
        this.mustPrefixWithSlash = mustPrefixWithSlash;
    }

    public String getHref() {
        return href;
    }

    protected final AbstractUrlPart setHref(final String href) {
        this.href = href;
        return this;
    }

    public boolean handleParameter(final Parameter param, final Object value) {
        return false;
    }

    protected final String valueToString(final Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public String getFinalResult() {
        if (!mustPrefixWithSlash) {
            return href;
        } else if (href.isEmpty() || href.startsWith("/")) {
            return href;
        }
        return '/' + href;
    }


}
