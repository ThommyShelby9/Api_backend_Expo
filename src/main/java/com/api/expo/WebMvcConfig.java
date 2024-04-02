package com.api.expo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.autoconfigure.web.WebProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import org.springframework.http.converter.HttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // @Override
    // public void addViewControllers(ViewControllerRegistry registry) {
    //     registry.addViewController("/").setViewName("home");
    // }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(new LocaleChangeInterceptor());
    // }

    // @Bean
    // public MessageSource messageSource() {
    //     ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    //     messageSource.setBasename("messages");
    //     return messageSource;
    // }

    // @Bean
    // public CookieLocaleResolver localeResolver() {
    //     CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    //     localeResolver.setDefaultLocale(Locale.ENGLISH);
    //     return localeResolver;
    // }

    @Override
    @SuppressWarnings({ "deprecation", "null", "unchecked" })
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = Arrays.asList(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            new MediaType("application", "json", StandardCharsets.UTF_8)
        );
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(jsonConverter);
    }
}

