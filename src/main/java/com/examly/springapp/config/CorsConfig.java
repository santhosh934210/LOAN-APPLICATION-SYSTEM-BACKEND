package com.examly.springapp.config;

import java.util.List;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    //application.properties I MENTIONED THESE VALUES
    // @Value("${app.cors.allowed-origins[0]:http://localhost:5173}")
    // private String allowedOrigin;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // cfg.setAllowedOrigins(List.of(allowedOrigin));
        //for testing
        cfg.setAllowedOriginPatterns(List.of("http://localhost:5173","http://localhost:5174","https://loan-application-system-santhosh.vercel.app")); // ✅ allow all localhost ports
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
