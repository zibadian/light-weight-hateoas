package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;

import java.lang.reflect.Method;

public abstract class AbstractResourceLinkBuilder extends LinkBuilder {

    public final <T> T getResource(final Class<T> resourceClass) {
        addPart(makeControllerPathPart(resourceClass));
        return HateoasProviderHelper.makeResourceWatcher(resourceClass, new InternalLinkBuilder());
    }

    protected abstract UrlPart makeMethodPathPart(final Method method);

    protected abstract UrlPart makeControllerPathPart(final Class<?> resourceClass);

    private class InternalLinkBuilder implements Invoker {

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] arguments) throws Throwable {
            addPart(makeMethodPathPart(method));
            return null;
        }

    }

}
