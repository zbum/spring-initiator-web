package com.zbum.example.springweb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.zbum.example.springweb",
        includeFilters = {
                @ComponentScan.Filter(
                        value = Controller.class,
                        type = FilterType.ANNOTATION
                )
        },
        excludeFilters = {
                @ComponentScan.Filter(
                    value = Service.class,
                    type = FilterType.ANNOTATION),
                @ComponentScan.Filter(
                    value = Repository.class,
                    type = FilterType.ANNOTATION)
        })
public class WebMvcConfig extends WebMvcConfigurerAdapter{

}
