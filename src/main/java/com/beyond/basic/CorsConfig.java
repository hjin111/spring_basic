
package com.beyond.basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        // 특정 도메인에 대해서 허용 정책을 냄
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:8082") // 허용 url 명시
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // 보안 처리 허용
    }

}
