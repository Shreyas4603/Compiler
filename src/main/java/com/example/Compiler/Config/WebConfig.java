package com.example.Compiler.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "http://13.234.29.166:8080",
                                "https://13.234.29.166:8080",
                                "https://ec2-13-234-29-166.ap-south-1.compute.amazonaws.com",
                                "http://ec2-13-234-29-166.ap-south-1.compute.amazonaws.com",
                                "http://65.2.126.202:9000",
                                "https://65.2.126.202:9000",
                                "https://ec2-65-2-126-202.ap-south-1.compute.amazonaws.com",
                                "http://ec2-65-2-126-202.ap-south-1.compute.amazonaws.com",
                                "http://15.206.209.169:8000",
                                "https://15.206.209.169:8000",
                                "https://ec2-15-206-209-169.ap-south-1.compute.amazonaws.com",
                                "http://ec2-15-206-209-169.ap-south-1.compute.amazonaws.com"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
