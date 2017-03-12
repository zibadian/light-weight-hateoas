package net.bart.hateoas.core.providers;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.proxy.FactoryException;
import net.bart.hateoas.core.proxy.Invoker;
import net.bart.hateoas.core.proxy.ProxyGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class HateoasProviderHelper {

    public static final String BASE_URI_KEY = "base_uri";
    private static HateoasProviderHelper INSTANCE;
    private final Class<? extends AbstractResourceLinkBuilder> resourceLinkBuilderClass;
    private Map<String, Class<?>> proxies = new ConcurrentHashMap<>();
    private ProxyGenerator proxyGenerator;
    private Map<String, Object> linkProperties = new HashMap<>();

    public HateoasProviderHelper(final Class<? extends AbstractResourceLinkBuilder> resourceLinkBuilderClass) {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
        this.resourceLinkBuilderClass = resourceLinkBuilderClass;
    }

    public static <T> T makeResourceWatcher(final Class<T> resourceClass, Invoker invoker) {
        return INSTANCE.getOrCreate(resourceClass, invoker);
    }

    public static Object getLinkProperty(final String name) {
        return INSTANCE.linkProperties.get(name);
    }

    public static Object setLinkProperty(final String name, final Object value) {
        return INSTANCE.linkProperties.put(name, value);
    }

    public static AbstractResourceLinkBuilder getResourceLinkBuilder() {
        try {
            return INSTANCE.resourceLinkBuilderClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new FactoryException("Failed to create resource link builder");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getOrCreate(final Class<T> resourceClass, Invoker invoker) {
        Class<?> proxyClass = proxies.get(resourceClass.getCanonicalName());
        if (proxyClass == null) {
            proxyClass = tryToCreateProxyClass(resourceClass);
        }
        if (proxyClass == Object.class) {
            throw new ClassCastException("Cannot find correct proxy to case " + resourceClass + " to.");
        }
        try {
            return (T) proxyClass
                    .getConstructor(Invoker.class)
                    .newInstance(invoker);
        } catch (Exception e) {
            throw e instanceof RuntimeException ? ((RuntimeException) e) : new RuntimeException(e);
        }
    }

    private Class<?> tryToCreateProxyClass(final Class<?> resourceClass) {
        Class<?> result;
        try {
            result = getProxyGenerator().generateProxyClass(resourceClass.getClassLoader(), resourceClass);
        } catch (FactoryException e) {
            result = Object.class;
        }
        proxies.put(resourceClass.getCanonicalName(), result);
        return result;
    }

    public ProxyGenerator getProxyGenerator() {
        if (proxyGenerator == null) {
            proxyGenerator = new ProxyGenerator();
        }
        return proxyGenerator;
    }

}
