package net.bart.hateoas.spring.processors;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.util.List;

public class SpringArgumentReturnProcessor extends AbstractMessageConverterMethodProcessor {

    @Autowired
    public SpringArgumentReturnProcessor(final List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getMethodAnnotation(Hateoas.class) != null &&
                HateoasContext.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        return new HateoasContext();
    }

    @Override
    public boolean supportsReturnType(final MethodParameter returnType) {
        return false;
    }

    @Override
    public void handleReturnValue(final Object returnValue, final MethodParameter returnType, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest) throws Exception {

    }

}
