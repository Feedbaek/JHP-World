package minskim2.JHP_World.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.interceptor.AnonymousVisitInterceptor;
import minskim2.JHP_World.config.interceptor.NotificationInterceptor;
import minskim2.JHP_World.config.interceptor.VisitorStatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final NotificationInterceptor notificationInterceptor;
    private final AnonymousVisitInterceptor anonymousVisitInterceptor;
    private final VisitorStatsInterceptor visitorStatsInterceptor;

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
        // 방문자 통계 인터셉터
        registry.addInterceptor(visitorStatsInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
    }

    @PostConstruct
    public void setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
