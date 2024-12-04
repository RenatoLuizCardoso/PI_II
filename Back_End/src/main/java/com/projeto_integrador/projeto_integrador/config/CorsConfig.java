package com.projeto_integrador.projeto_integrador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
 
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8081", "https://main.d32fywy3goxad7.amplifyapp.com", "https://master.d3sgsryxh8v9ff.amplifyapp.com")
                        .allowedMethods("*");
            }
 
        };
 
    }
    
}
