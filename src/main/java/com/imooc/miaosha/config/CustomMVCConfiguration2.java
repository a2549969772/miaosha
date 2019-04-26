package com.imooc.miaosha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.nio.charset.Charset;
import java.util.List;

//@Configuration
public class CustomMVCConfiguration2 extends WebMvcConfigurationSupport {
    //https://www.it399.com/blog/web/201805081017
    @Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        super.configureMessageConverters(converters);
        //解决中文乱码
        converters.add(responseBodyConverter());
        //解决 解决中文乱码 问题后出现 上述配置之后，返回json数据直接报错 500：no convertter for return value of type
        converters.add(messageConverter());

    }


}