package com.imooc.miaosha.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

////@Configuration
//public class CustomMVCConfiguration extends WebMvcConfigurationSupport {
//    //@Bean
////    public HttpMessageConverter<String> responseBodyConverter(){
////        StringHttpMessageConverter converter=new StringHttpMessageConverter(Charset.forName("UTF-8"));
////        return  converter;
////    }
//    //@Bean
//    public HttpMessageConverter<String> responseBodyConverter(){
//         StringHttpMessageConverter converter=new StringHttpMessageConverter(Charset.forName("UTF-8"));
//         return converter;
//    }
//
//
//    //@Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(responseBodyConverter());
//        super.configureMessageConverters(converters);
//    }
//
//    //@Override
//    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }
//}
