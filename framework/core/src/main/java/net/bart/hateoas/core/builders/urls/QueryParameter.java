package net.bart.hateoas.core.builders.urls;

public final class QueryParameter<T> {

    private final String name;
    private T value;
    private boolean mustInclude = false;
    private boolean hasBeenSet = false;

    public QueryParameter(final String name, final T value) {
        this.name = name;
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public QueryParameter<T> setValue(final T value) {
        this.value = value;
        hasBeenSet = value != null;
        return this;
    }

    public boolean isMustInclude() {
        return mustInclude;
    }

    public QueryParameter<T> setMustInclude(final boolean mustInclude) {
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
