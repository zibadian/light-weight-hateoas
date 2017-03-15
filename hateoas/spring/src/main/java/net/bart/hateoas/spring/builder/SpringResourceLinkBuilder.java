package net.bart.hateoas.spring.builder;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.net.URI;

public class SpringResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(Method method) {
        RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        return new UrlPathPart(annotation.value()[0]);
    }

    @Override
    protected UrlPart makeControllerPathPart(Class<?> resourceClass) {
        RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(resourceClass, RequestMapping.class);
        if (annotation == null || annotation.value().length == 0) {
            return new UrlPathPart("");
        } else {
            return new UrlPathPart(annotation.value()[0]);
        }
    }

}
