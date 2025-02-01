package minskim2.JHP_World.config.login.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import minskim2.JHP_World.domain.visitor_log.service.VisitorLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "AdminSuccessHandler")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminSuccessHandler implements AuthenticationSuccessHandler {

    private final VisitorLogService visitorLogService;
    private final NotificationService notificationService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그인 성공 로깅
        visitorLogService.loginLog(request, authentication);
        // 알림 확인
        checkNotification(request, authentication);
        // 로그인 성공 시 리다이렉트
        response.sendRedirect("/admin/home");
    }

    // 다음 필터 체이닝 호출 과정을 명시적으로 보이게 하기 위해 추가
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }


    public void checkNotification(HttpServletRequest request, Authentication authentication) {
        // OAuth2 로그인 사용자와 일반 로그인 사용자
        CustomOAuth2User member = (CustomOAuth2User) authentication.getPrincipal();

        // 사용자 ID 가져오기
        Long userId = member.getMemberId(); // Spring Security로 로그인된 사용자
        // DB에서 알림 여부 확인
        boolean hasUnreadNotifications = notificationService.hasUnreadNotifications(userId);

        // 세션에 알림 표시 저장
        if (hasUnreadNotifications) {
            HttpSession session = request.getSession();
            session.setAttribute("notification", true);
        }
    }
}
