package net.bart.hateoas.core.builders.urls;

import java.util.HashSet;
import java.util.Set;

public final class QueryParamPart extends BaseUrlPart {

    private final Set<UrlParameter> parameters = new HashSet<>();

    public QueryParamPart() {
        super("", false);
    }

    public Set<UrlParameter> getParameters() {
        return parameters;
    }

    public UrlParameter add(final String name, final Object value) {
        final UrlParameter result;
        if (value == null) {
            result = new UrlParameter(name, null);
        } else {
            result = new UrlParameter(name, value.toString());
        }
        parameters.add(result);
        return result;
    }

    @Override
    public String getHref() {
        StringBuilder builder = new StringBuilder();
        for(UrlParameter parameter : parameters) {
            builder.append(parameter.getFinalResult());
        }
        if (builder.length() > 0) {
            builder.replace(0, 1, "?");
        }
        return builder.toString();
    }

}

