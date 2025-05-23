package com.catchtable.config;

import com.catchtable.resolver.AuthTokenResolver;
import com.catchtable.resolver.PageSizeResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthTokenResolver authTokenResolver;
    private final PageSizeResolver pageSizeResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authTokenResolver);
        resolvers.add(pageSizeResolver);
    }
}
