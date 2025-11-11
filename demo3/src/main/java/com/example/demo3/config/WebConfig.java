package com.example.demo3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/board/**")
        .addResourceLocations("classpath:/static/data/board/")
        .setCachePeriod(20);

    registry.addResourceHandler("/pds/**")
        .addResourceLocations("classpath:/static/data/pds/")
        .setCachePeriod(20);;
  }
}
