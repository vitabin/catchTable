package com.catchtable.resolver;

import com.catchtable.annotation.Pagination;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageSizeResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Pagination.class)
            && Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Pagination annotation = parameter.getParameterAnnotation(Pagination.class);

        int pageSize = Objects.requireNonNull(annotation)
                              .pageSize();
        int pageNum = annotation.pageNum();
        int maxSize = annotation.maxSize();

        int page = parseInt(webRequest.getParameter("page"), pageNum);
        int size = parseInt(webRequest.getParameter("pageSize"), pageSize);

        if (size > maxSize) {
            size = maxSize;
        }

        return PageRequest.of(page, size);
    }

    private int parseInt(String param, int defaultValue) {
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
