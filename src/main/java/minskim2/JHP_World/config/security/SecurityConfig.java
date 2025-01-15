package minskim2.JHP_World.config.security;

import minskim2.JHP_World.config.EnvBean;
import minskim2.JHP_World.config.login.handler.FailureHandler;
import minskim2.JHP_World.config.login.handler.SuccessHandler;
import minskim2.JHP_World.config.login.oauth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.visitor_log.service.VisitorLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// spring security 설정 파일 입니다.
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity() // prePostEnabled 어노테이션 활성화
public class SecurityConfig {

    private final EnvBean envBean;
    private final OAuth2Service oAuth2Service;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    private final VisitorLogService visitorLogService;

    // GET 메소드 허용 경로
    private final String[] GET_LIST = {
        // 인덱스 페이지
        "/",
        // 소셜 로그인 페이지
        "/login",
        // 정적 리소스
        "/css/**",
        "/js/**",

        // Home 페이지
        "/home",
        // 강의 별 과제 목록 조회
        "/lecture/*/assignmentList",
        // 과제 상세 조회
        "/assignment/*",
        "/post/*"
    };

    // 모든 사용자 허용 경로
    private final String[] WHITE_LIST = {
        // swagger
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/swagger-resources/**",

        // 에러 페이지
        "/error",
    };

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인 설정
        http
            // .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
//            .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
            // oauth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                    .loginPage(envBean.getLoginUrl()) // 로그인 페이지
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(oAuth2Service)) // 사용자 정보 가져오는 서비스
                    .successHandler(successHandler) // 로그인 성공 핸들러
                    .failureHandler(failureHandler) // 로그인 실패 핸들러
                    .permitAll()) // 로그인 페이지는 모든 사용자 허용
            // 폼 로그인 설정
            .formLogin(form -> form
                    .loginPage("/admin/login") // 로그인 페이지
                    .successHandler(successHandler) // 로그인 성공 핸들러
                    ) // 로그인 페이지는 모든 사용자 허용

            // 세션 설정
            .sessionManagement(session -> session
                    .sessionFixation().migrateSession()
                    .sessionAuthenticationStrategy(new CustomSessionFixationProtectionStrategy()) // 세션 고정 보호
                    .maximumSessions(1) // 최대 세션 수
                    .maxSessionsPreventsLogin(true) // 중복 로그인 방지
            )
            // 로그아웃 설정
            .logout(logout -> logout
                    .logoutUrl(envBean.getLogoutUrl()) // 로그아웃 경로
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
                    .logoutSuccessHandler((request, response, authentication) ->
                            response.sendRedirect("/home")) // 로그아웃 성공 시 home 페이지로 리다이렉트
                    .permitAll()) // 로그아웃 페이지는 모든 사용자 허용
            // 로그인 예외 처리
            .exceptionHandling(exception -> exception
                    // 인증 실패 핸들러
//                    .authenticationEntryPoint((request, response, authException) -> {
//                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    })
                    // 권한 없음 핸들러
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    })
            )
            // 허용 경로 설정
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET, GET_LIST).permitAll() // GET 메소드 허용 경로 (GET 메소드)
                    .requestMatchers(WHITE_LIST).permitAll() // 모든 사용자 허용 경로 (모든 메소드)
//                    .anyRequest().permitAll() // 그 외 나머지 경로는 전부 모든 사용자 허용
                    .anyRequest().authenticated() // 그 외 나머지 경로는 전부 인증 필요
            );

        return http.build();
    }

    // 비밀번호 암호화 구현체
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}