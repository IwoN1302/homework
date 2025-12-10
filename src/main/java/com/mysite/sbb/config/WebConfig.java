package com.mysite.sbb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /uploads/** URL to the physical file path in
        // src/main/resources/static/uploads
        // "file:///" + System.getProperty("user.dir") +
        // "/src/main/resources/static/uploads/"

        String uploadPath = "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
