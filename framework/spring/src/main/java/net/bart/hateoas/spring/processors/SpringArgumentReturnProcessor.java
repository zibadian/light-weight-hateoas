package net.bart.hateoas.spring.processors;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.LinkProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SpringArgumentReturnProcessor implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return HateoasContext.class.isAssignableFrom(parameter.getParameterType()) &&
                parameter.getParameterAnnotation(Hateoas.class) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        return new HateoasContext(new LinkProvider() {
            @Override
            public LinkBuilder provideSelfLink() {
                return new LinkBuilder().addPart(new UrlPathPart(((ServletWebRequest) webRequest).getRequest().getRequestURI()));
            }

        });
    }

}
