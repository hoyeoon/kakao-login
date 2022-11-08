package kakao.login.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .exposedHeaders("*")
                .allowCredentials(true);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}