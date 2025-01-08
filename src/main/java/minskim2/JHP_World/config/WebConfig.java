package minskim2.JHP_World.config;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.interceptor.AnonymousVisitInterceptor;
import minskim2.JHP_World.config.interceptor.NotificationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final NotificationInterceptor notificationInterceptor;
    private final AnonymousVisitInterceptor anonymousVisitInterceptor;

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
     registry.addMapping("/**")
             .allowedOriginPatterns("*")
             .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
             .allowedHeaders("*")
             .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 알림 확인 인터셉터
        registry.addInterceptor(notificationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
        // 익명 방문자 로깅 인터셉터
        registry.addInterceptor(anonymousVisitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
    }
}
