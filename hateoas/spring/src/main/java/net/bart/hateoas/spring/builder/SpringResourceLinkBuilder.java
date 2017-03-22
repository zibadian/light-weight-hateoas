package net.bart.hateoas.spring.builder;

import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class SpringResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    protected UrlPart makeMethodPathPart(Method method, final Object[] arguments) {
        RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        String href = annotation.value()[0];
        final Map<String, Object> parameters = mapParametersToArguments(method.getParameters(), arguments);
        href = processURITemplate(href, parameters);
        return new UrlPathPart(href);
    }

    private String processURITemplate(final String href, final Map<String, Object> parameters) {
        String result = href;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            result = result.replaceAll("\\{" + entry.getKey() + "\\}", String.valueOf(entry.getValue()));
        }
        return result;
    }

    private Map<String, Object> mapParametersToArguments(final Parameter[] parameters, final Object[] arguments) {
        Map<String, Object> result = new HashMap<>();
        if (parameters.length != arguments.length) {
            throw new HateoasException("Expected parameters and arguments to be the same length");
        }
        for (int i = 0; i < parameters.length; i++) {
            addValue(parameters[i], arguments[i], result);
        }
        return result;
    }

    private void addValue(final Parameter parameter, Object argument, final Map<String, Object> result) {
        final PathVariable pathParam = parameter.getAnnotation(PathVariable.class);
        final RequestParam queryParam = parameter.getAnnotation(RequestParam.class);
        if (pathParam != null) {
            result.put(pathParam.value(), argument);
        }
        if (queryParam != null) {
            if (argument == null) {
                argument = queryParam.defaultValue();
            }
            result.put(queryParam.value(), argument);
        }
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
