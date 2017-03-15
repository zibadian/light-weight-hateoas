package net.bart.hateoas.core.builders.urls;

import net.bart.hateoas.core.builders.UrlPart;

public abstract class AbstractUrlPart implements UrlPart {

    private String href;

    public AbstractUrlPart(final String href) {
        setHref(href);
    }

    @Override
    public String getHref() {
        return href;
    }

    protected final AbstractUrlPart setHref(final String href) {
        this.href = href;
        return this;
    }

}
