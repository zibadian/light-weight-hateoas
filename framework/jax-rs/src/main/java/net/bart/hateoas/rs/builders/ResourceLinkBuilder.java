package net.bart.hateoas.rs.builders;

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

public class ResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(final Method method) {
        String href = UriBuilder
                .fromMethod(method.getDeclaringClass(), method.getName())
                .toTemplate();
        try {
            href = URLDecoder.decode(href, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return new UrlPathPart(href);
    }

    @Override
    protected UrlPart makeControllerPathPart(final Class<?> declaringClass) {
        return new UrlPathPart(UriBuilder.fromResource(declaringClass).build().toString());
    }

    @Override
    protected void processParameter(final Parameter parameter, Object argument, final StringBuilder builder) {
        final PathParam pathParam = parameter.getAnnotation(PathParam.class);
        final QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
        final DefaultValue defaultValue = parameter.getAnnotation(DefaultValue.class);
        if (argument == null && defaultValue != null) {
            argument = defaultValue.value();
        }
        if (pathParam != null) {
            replaceAll('{' + pathParam.value() + '}', argument, builder);
        } else if (queryParam != null) {
            replaceAll('{' + queryParam.value() + '}', argument, builder);
        }
    }

}
