package com.web.curation.config;

import com.web.curation.Jwt.JwtInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] EXCLUDE_PATHS = {
            "/account/**",
            "/error/**",
            "/users/**",
            "/friends/**",
            "/swagger-ui.html",
            "/feeds/**",
            "/comments/**",
            "/requests/**",
            "/alerts/**"
    };

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(jwtInterceptor)
                  .addPathPatterns("/**")
                  .excludePathPatterns(EXCLUDE_PATHS);
    }
}