//package com.happycode.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 允许所有请求来源
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173") // 允许的前端地址
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
//                .allowedHeaders("*") // 允许的头部
//                .allowCredentials(true); // 允许带上 Cookie
//    }
//}
