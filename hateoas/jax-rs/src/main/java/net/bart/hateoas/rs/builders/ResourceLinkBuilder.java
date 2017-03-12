package net.bart.hateoas.rs.builders;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;

import javax.ws.rs.core.UriBuilder;
import java.lang.reflect.Method;

public class ResourceLinkBuilder extends AbstractResourceLinkBuilder {

    public <T> T getResource(final Class<T> resourceClass) {
        return HateoasProviderHelper.makeResourceWatcher(resourceClass, new InternalLinkBuilder());
    }

    private class InternalLinkBuilder implements Invoker {

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] arguments) throws Throwable {
            setInternalLink(UriBuilder
                    .fromResource(method.getDeclaringClass())
                    .path(method)
                    .build());
            return null;
        }

    }

}
