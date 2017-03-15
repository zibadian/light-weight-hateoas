package net.bart.hateoas.rs.builders;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;

import javax.ws.rs.core.UriBuilder;
import java.lang.reflect.Method;

public class ResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(final Method method) {
        return new UrlPathPart(UriBuilder.fromMethod(method.getDeclaringClass(), method.getName()).build().toString());
    }

    @Override
    protected UrlPart makeControllerPathPart(final Class<?> declaringClass) {
        return new UrlPathPart(UriBuilder.fromResource(declaringClass).build().toString());
    }

}
