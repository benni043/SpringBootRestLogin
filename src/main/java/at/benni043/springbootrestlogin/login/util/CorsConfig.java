package at.benni043.springbootrestlogin.login.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Add the URL patterns for which you want to enable CORS
                .allowedOrigins("*")  // Specify the allowed origins, or use specific domain instead of "*"
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify the allowed HTTP methods
                .allowedHeaders("*");  // Specify the allowed headers
    }

}
