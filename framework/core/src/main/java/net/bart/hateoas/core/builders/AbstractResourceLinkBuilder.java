package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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

    protected abstract void processParameter(final Parameter parameter, final Object argument, final StringBuilder builder);

    private UrlPart makeMethodPathPart0(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
        return makeMethodPathPart(method);
    }

    @Override
    public final String build() {
        final String result = super.build();
        if (result == null) {
            return null;
        }
        return fillParameters(method, arguments, result);
    }

    protected final String fillParameters(final Method method, final Object[] arguments, final String URI) {
        StringBuilder builder = new StringBuilder(URI);
        final Parameter[] parameters = method.getParameters();
        if (parameters.length != arguments.length) {
            throw new HateoasException("Expected parameters and arguments to be the same length");
        }
        for (int i = 0; i < parameters.length; i++) {
            processParameter(parameters[i], arguments[i], builder);
        }
        return builder.toString();
    }

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

    protected final void replaceAll(final String name, final Object argument, final StringBuilder builder) {
        int start = builder.indexOf(name);
        while (start > -1) {
            final int end = start + name.length();
            if (argument == null) {
                builder.replace(start, end, "");
            } else {
                builder.replace(start, end, argument.toString());
            }
            start = builder.indexOf(name);
        }
    }

}
