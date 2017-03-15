package net.bart.hateoas.spring.builder;

final class LinkParameter {

    private final String name;
    private String value;
    private boolean mustInclude = false;
    private boolean hasBeenSet = false;

    LinkParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }

    LinkParameter setValue(final String value) {
        this.value = value;
        hasBeenSet = value != null;
        return this;
    }

    LinkParameter setMustInclude(final boolean mustInclude) {
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
