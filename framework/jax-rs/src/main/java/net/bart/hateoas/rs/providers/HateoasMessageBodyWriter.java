package net.bart.hateoas.rs.providers;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.builders.HateoasVisitorManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class HateoasMessageBodyWriter extends JacksonJaxbJsonProvider {

    @Inject
    private HateoasVisitorManager visitorManager;

    @Override
    public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return type.equals(HateoasContext.class);
    }

    @Override
    public void writeTo(final Object value, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException {
        if (value instanceof HateoasContext) {
            visitorManager.createVisitorHandler((HateoasContext) value).visit(value);
        }
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    public static class Binder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(HateoasVisitorManager.class)
                    .to(HateoasVisitorManager.class)
                    .in(Singleton.class);
        }
    }
}
