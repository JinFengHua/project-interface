package com.jdk.projectinterface.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class UploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\";
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + imagePath);
    }
}
