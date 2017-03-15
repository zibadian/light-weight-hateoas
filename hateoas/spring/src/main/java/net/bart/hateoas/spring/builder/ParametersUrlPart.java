package net.bart.hateoas.spring.builder;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ParametersUrlPart extends AbstractUrlPart {

    private List<LinkParameter> parameters = new ArrayList<>();

    public ParametersUrlPart(final Method method) {
        super("", false);
        final RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (mapping != null) {
            final String href = mapping.path()[0];
            int i = href.indexOf('?');
            if (i > -1) {
                makeParameters(href.substring(i + 1).split("[&=]"));
            }
        }
    }

    private void makeParameters(final String[] parameters) {
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.add(new LinkParameter(parameters[i], parameters[i + 1]));
        }
    }

    @Override
    public boolean handleParameter(final Parameter param, final Object value) {
        final RequestParam paramAnnotation = param.getAnnotation(RequestParam.class);
        if (paramAnnotation != null) {
            for (LinkParameter parameter : parameters) {
                processParameter(paramAnnotation, value, parameter);
            }
            return true;
        }
        return false;
    }

    private void processParameter(final RequestParam paramAnnotation, final Object value, final LinkParameter parameter) {
        final String paramValuePlaceHolder = "{" + paramAnnotation.value() + "}";
        if (paramValuePlaceHolder.equals(parameter.getValue())) {
            parameter.setMustInclude(paramAnnotation.required());
            if (value != null) {
                parameter.setValue(value.toString());
            }
        }
    }

    @Override
    public String getFinalResult() {
        StringBuilder builder = new StringBuilder();
        for (LinkParameter parameter : parameters) {
            builder.append(parameter.getFinalResult());
        }
        if (builder.length() > 0) {
            return builder.replace(0, 1, "?").toString();
        } else {
            return "";
        }
    }

}
