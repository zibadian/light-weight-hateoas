package net.bart.hateoas.rs.providers;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;
import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.LinkProvider;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

public class HateoasContextInjectionResolver extends ParamInjectionResolver<Hateoas> {

    public HateoasContextInjectionResolver() {
        super(HateoasContextValueFactoryProvider.class);
    }

    @Singleton
    private static class HateoasContextValueFactoryProvider extends AbstractValueFactoryProvider {

        @Inject
        public HateoasContextValueFactoryProvider(final MultivaluedParameterExtractorProvider mpep, final ServiceLocator locator) {
            super(mpep, locator, Parameter.Source.UNKNOWN);
        }

        @Override
        protected Factory<?> createValueFactory(final Parameter parameter) {
            if (!parameter.isAnnotationPresent(Hateoas.class)
                    || !HateoasContext.class.equals(parameter.getRawType())) {
                return null;
            }
            return new HateoasContextFactory();
        }

    }

    private static class HateoasContextFactory extends AbstractContainerRequestValueFactory<HateoasContext> implements LinkProvider {

        @Override
        public LinkBuilder provideSelfLink() {
            return new LinkBuilder().addPart(new UrlPathPart(getContainerRequest().getRequestUri().toString()));
        }

        @Override
        public HateoasContext provide() {
            final HateoasContext result = new HateoasContext(this);
            getContainerRequest().getPropertiesDelegate().setProperty(HateoasAware.HATEOAS_CONTEXT_PROPERTY, result);
            return result;
        }
    }

    public static class Binder extends AbstractBinder {

        @Override
        public void configure() {
            bind(HateoasContextValueFactoryProvider.class)
                    .to(ValueFactoryProvider.class)
                    .in(Singleton.class);
            bind(HateoasContextInjectionResolver.class)
                    .to(new TypeLiteral<InjectionResolver<Hateoas>>() {
                    })
                    .in(Singleton.class);
        }
    }

}
