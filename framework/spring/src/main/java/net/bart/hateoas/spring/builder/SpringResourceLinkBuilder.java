package net.bart.hateoas.spring.builder;

import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SpringResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(Method method) {
        RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        String href = annotation.value()[0];
        return new UrlPathPart(href);
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

    @Override
    protected void processParameter(final Parameter parameter, final Object argument, final StringBuilder builder) {
        final PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        final RequestParam queryParam = parameter.getAnnotation(RequestParam.class);
        if (pathVariable != null) {
            replaceAll('{' + findName(pathVariable) + '}', argument, builder);
        } else if (queryParam != null) {
            replaceAll('{' + findName(queryParam) + '}', argument, builder);
        }
    }

    private String findName(final PathVariable pathVariable) {
        if (!StringUtils.isEmpty(pathVariable.value())) {
            return pathVariable.value();
        } else if (!StringUtils.isEmpty(pathVariable.name())) {
            return pathVariable.name();
        } else {
            return "";
        }
    }

    private String findName(final RequestParam requestParam) {
        if (!StringUtils.isEmpty(requestParam.value())) {
            return requestParam.value();
        } else if (!StringUtils.isEmpty(requestParam.name())) {
            return requestParam.name();
        } else {
            return "";
        }
    }

}
