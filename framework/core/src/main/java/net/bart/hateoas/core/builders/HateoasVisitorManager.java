package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.HateoasLinks;
import net.bart.hateoas.core.builders.visitors.CollectionVisitor;
import net.bart.hateoas.core.builders.visitors.HateoasAwareVisitor;
import net.bart.hateoas.core.builders.visitors.MapVisitor;
import net.bart.hateoas.core.builders.visitors.VisitorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class HateoasVisitorManager {

    public static final AbstractVisitor<HateoasLinks> HATEOAS_LINKS_VISITOR = new AbstractVisitor<HateoasLinks>() {
        @Override
        public void visit(final VisitorHandler visitorHandler, final HateoasLinks object) {

        }

        @Override
        public boolean hasFieldsToVisit(final Object object) {
            return false;
        }
    };
    private static final AbstractVisitor<Object> OBJECT_LINKS_VISITOR = new AbstractVisitor<Object>() {
        @Override
        public void visit(final VisitorHandler visitorHandler, final Object object) {

        }
    };
    private static HateoasVisitorManager INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(VisitorHandler.class);

    private Map<Class<?>, AbstractVisitor<?>> visitorRegistration = new HashMap<>();

    public HateoasVisitorManager() {
        visitorRegistration.put(HateoasAware.class, new HateoasAwareVisitor<>());
        visitorRegistration.put(HateoasLinks.class, HATEOAS_LINKS_VISITOR);
        visitorRegistration.put(Object.class, OBJECT_LINKS_VISITOR);
        visitorRegistration.put(Collection.class, new CollectionVisitor());
        visitorRegistration.put(Map.class, new MapVisitor());
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    public static HateoasVisitorManager getInstance() {
        return INSTANCE;
    }

    public <T> void register(Class<T> objectType, Class<? extends AbstractVisitor<? extends T>> visitor) {
        log.debug("Registering " + visitor.getCanonicalName() + " for " + objectType.getCanonicalName());
        try {
            if (!objectType.equals(HateoasLinks.class)) {
                visitorRegistration.put(objectType, visitor.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Failed to register " + visitor.getCanonicalName(), e);
        }
    }

    public final AbstractVisitor<?> get(final Class<?> key) {
        return visitorRegistration.get(key);
    }

    public VisitorHandler createVisitorHandler(final HateoasContext context) {
        return new VisitorHandler(this, context);
    }

}
