package net.bart.hateoas.core.builders.urls;

public final class UrlParameter {

    private final String name;
    private String value;
    private boolean mustInclude = false;
    private boolean hasBeenSet = false;

    public UrlParameter(final String name, final String value) {
        this.name = name;
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public UrlParameter setValue(final String value) {
        this.value = value;
        hasBeenSet = value != null;
        return this;
    }

    public boolean isMustInclude() {
        return mustInclude;
    }

    public UrlParameter setMustInclude(final boolean mustInclude) {
        this.mustInclude = mustInclude;
        return this;
    }

    String getFinalResult() {
        if (hasBeenSet) {
            return '&' + name + '=' + value;
        } else if (mustInclude) {
            return '&' + name + '=';
        }
        return "";
    }

}