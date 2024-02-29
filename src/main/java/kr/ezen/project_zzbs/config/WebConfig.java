package kr.ezen.project_zzbs.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://43.200.173.234")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

