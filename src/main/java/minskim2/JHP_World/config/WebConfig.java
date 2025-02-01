package minskim2.JHP_World.config;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.interceptor.AnonymousVisitInterceptor;
import minskim2.JHP_World.config.interceptor.AuthorizationInterceptor;
import minskim2.JHP_World.config.interceptor.VisitorStatsInterceptor;
import minskim2.JHP_World.config.resolver.PageArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AnonymousVisitInterceptor anonymousVisitInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;
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

        // 익명 방문자 로깅 인터셉터
        registry.addInterceptor(anonymousVisitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");

        // 어드민 계정 확인 인터셉터
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/api/admin/**");

        // 방문자 통계 인터셉터
        registry.addInterceptor(visitorStatsInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        // 커스텀한 ArgumentResolver를 추가
        resolvers.add(new PageArgumentResolver());
    }

    @PostConstruct
    public void setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
