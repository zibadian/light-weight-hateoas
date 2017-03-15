package net.bart.hateoas.core.builders.urls;

import net.bart.hateoas.core.builders.UrlPart;

import java.util.HashSet;
import java.util.Set;

public final class QueryParamPart implements UrlPart {

    private final Set<QueryParameter> parameters = new HashSet<>();

    public Set<QueryParameter> getParameters() {
        return parameters;
    }

    public QueryParameter add(final String name, final Object value) {
        final QueryParameter result;
        if (value == null) {
            result = new QueryParameter(name, null);
        } else {
            result = new QueryParameter(name, value.toString());
        }
        parameters.add(result);
        return result;
    }

    @Override
    public String getHref() {
        StringBuilder builder = new StringBuilder();
        for(QueryParameter parameter : parameters) {
            builder.append(parameter.getFinalResult());
        }
        if (builder.length() > 0) {
            builder.replace(0, 1, "?");
        }
        return builder.toString();
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE-1;
    }

}

