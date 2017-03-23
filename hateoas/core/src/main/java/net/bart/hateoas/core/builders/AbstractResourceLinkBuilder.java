package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AbstractResourceLinkBuilder extends LinkBuilder {

    private static Logger log = LoggerFactory.getLogger(HateoasContext.class);
    private Method method;
    private Object[] arguments;

    public final <T> T getResource(final Class<T> resourceClass) {
        addPart(makeControllerPathPart(resourceClass));
        return HateoasProviderHelper.makeResourceWatcher(resourceClass, new InternalLinkBuilder());
    }

    protected abstract UrlPart makeMethodPathPart(final Method method);

    protected abstract UrlPart makeControllerPathPart(final Class<?> resourceClass);

    private UrlPart makeMethodPathPart0(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
        return makeMethodPathPart(method);
    }

    @Override
    public String build() {
        return fillParameters(method, arguments, super.build());
    }

    protected abstract String fillParameters(final Method method, final Object[] arguments, final String URI);

    private class InternalLinkBuilder implements Invoker {

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] arguments) throws Throwable {
            try {
                addPart(makeMethodPathPart0(method, arguments));
            } catch (NullPointerException npe) {
                log.warn(String.format("Failed to create method part for %s.%s()\n cause: Missing path describing annotation",
                        method.getDeclaringClass().getCanonicalName(), method.getName()));
                setErrorsDetected(true);
            } catch (Exception e) {
                log.error(String.format("Failed to create method part for %s.%s()",
                        method.getDeclaringClass().getCanonicalName(), method.getName()), e);
                setErrorsDetected(true);
            }
            return null;
        }

    }

}
