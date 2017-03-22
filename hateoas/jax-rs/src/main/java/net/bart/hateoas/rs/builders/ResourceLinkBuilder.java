package net.bart.hateoas.rs.builders;

import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(final Method method, final Object[] arguments) {
        final List<Object> paramValues = mapURIVariableParameters(method.getParameters(), arguments);
        String href = UriBuilder
                .fromMethod(method.getDeclaringClass(), method.getName())
                .build(paramValues.toArray(new Object[paramValues.size()]))
                .toString();
        try {
            href = URLDecoder.decode(href, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return new UrlPathPart(href);
    }

    private List<Object> mapURIVariableParameters(final Parameter[] parameters, final Object[] arguments) {
        final List<Object> result = new ArrayList<>();
        if (parameters.length != arguments.length) {
            throw new HateoasException("Expected parameters and arguments to be the same length");
        }
        for (int i = 0; i < parameters.length; i++) {
            addValue(parameters[i], arguments[i], result);
        }
        return result;
    }

    private void addValue(final Parameter parameter, Object argument, final List<Object> result) {
        final PathParam pathParam = parameter.getAnnotation(PathParam.class);
        final QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
        final DefaultValue defaultValue = parameter.getAnnotation(DefaultValue.class);
        if (argument == null && defaultValue != null) {
            argument = defaultValue.value();
        }
        if (pathParam != null) {
            result.add(argument);
        }
        if (queryParam != null) {
            result.add(argument);
        }
    }

    @Override
    protected UrlPart makeControllerPathPart(final Class<?> declaringClass) {
        return new UrlPathPart(UriBuilder.fromResource(declaringClass).build().toString());
    }

}
