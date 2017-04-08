package net.bart.hateoas.core.builders.visitors;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.builders.AbstractVisitor;
import net.bart.hateoas.core.builders.HateoasVisitorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class VisitorHandler {

    private final HateoasVisitorManager manager;
    private final HateoasContext context;
    private Map<Object, AbstractVisitor<?>> visitedFields = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(VisitorHandler.class);

    public VisitorHandler(final HateoasVisitorManager manager, final HateoasContext context) {
        this.manager = manager;
        this.context = context;
    }

    public HateoasContext getContext() {
        return context;
    }

    public void visit(final Object object) {
        try {
            log.debug("Start visiting " + object);
            internalVisit(object);
        } finally {
            log.debug("Finished visiting " + object);
        }
    }

    private void internalVisit(final Object object) {
        if (object == null || object.getClass().isPrimitive() || visitedFields.containsKey(object)) {
            // Nothing to do
        } else if (object.getClass().isArray() && !object.getClass().getComponentType().isPrimitive()) {
            visitedFields.put(object, null);
            visitArray((Object[]) object);
        } else {
            visitObject(object);
        }
    }

    private <T> void visitArray(final T[] array) {
        for (T element : array) {
            internalVisit(element);
        }
    }

    private void visitObject(final Object object) {
        //noinspection unchecked
        final AbstractVisitor<Object> visitor = (AbstractVisitor<Object>) findBestMatchingVisitor(object.getClass());
        try {
            visitor.visit(this, object);
            if (visitor.hasFieldsToVisit(object)) {
                processFields(object);
            }
        } catch (Exception e) {
            log.error("Error visiting: " + object.getClass(), e);
        } finally {
            visitedFields.put(object, visitor);
        }
    }

    private void processFields(final Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                    ) {
                try {
                    internalVisit(method.invoke(object));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Unable to invoke " + method.getName(), e);
                }
            }
        }
    }

    private AbstractVisitor<?> findBestMatchingVisitor(Class<?> visitedClass) {
        AbstractVisitor<?> result;
        do {
            result = matchingVisitorFor(visitedClass, visitedClass.getInterfaces());
            visitedClass = visitedClass.getSuperclass();
        } while (result == null);
        return result;
    }

    private AbstractVisitor<?> matchingVisitorFor(final Class<?> visitedClass, final Class<?>[] interfaces) {
        AbstractVisitor<?> result = manager.get(visitedClass);
        if (result != null) {
            return result;
        }
        for (Class<?> i : interfaces) {
            result = manager.get(i);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}
