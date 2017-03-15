package net.bart.hateoas.spring.builder;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public class ControllerClassUrlPart extends AbstractUrlPart {

    public ControllerClassUrlPart(final Class<?> controllerClass) {
        super("", true);
        final RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RequestMapping.class);
        if (mapping != null) {
            setHref(mapping.path()[0]);
        }
    }

}
