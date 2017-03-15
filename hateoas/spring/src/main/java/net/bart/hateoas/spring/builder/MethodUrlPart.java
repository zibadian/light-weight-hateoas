package net.bart.hateoas.spring.builder;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zs.hateoas.core.linkbuilder.AbstractUrlPart;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodUrlPart extends AbstractUrlPart {

    public MethodUrlPart(final Method method) {
        super("", true);
        final RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (mapping != null) {
            final String href = mapping.path()[0];
            int i = href.indexOf('?');
            if (i == -1) {
                setHref(href);
            } else {
                setHref(href.substring(0, i));
            }
        }
    }

    @Override
    public boolean handleParameter(final Parameter param, final Object value) {
        final PathVariable paramAnnotation = param.getAnnotation(PathVariable.class);
        if (paramAnnotation != null) {
            setHref(getHref().replaceAll("\\{" + paramAnnotation.value() + "\\}", valueToString(value)));
            return true;
        }
        return false;
    }

}
