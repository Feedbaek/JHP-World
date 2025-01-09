package minskim2.JHP_World.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.config.login.defalult.DefaultUser;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 로그인 사용자 확인
        Principal principal = request.getUserPrincipal();
        CustomOAuth2User member;

        // 로그인 사용자인 경우
        if (principal != null) {
            // OAuth2 로그인 사용자
            if (principal instanceof OAuth2AuthenticationToken token) {
                member = (CustomOAuth2User) token.getPrincipal();
            } // 일반 로그인 사용자
            else if (principal instanceof UsernamePasswordAuthenticationToken token) {
                member = (DefaultUser) token.getPrincipal();
            } // 로그인 사용자가 아닌 경우
            else {
                return true;
            }

            // 사용자 ID 가져오기
            Long userId = member.getMemberId(); // Spring Security로 로그인된 사용자
            // Redis에서 알림 여부 확인
            boolean hasUnreadNotifications = notificationService.hasUnreadNotifications(userId);

            // 세션에 알림 표시 저장
            if (hasUnreadNotifications) {
                HttpSession session = request.getSession();
                session.setAttribute("notification", true);
            }
        }
        return true;
    }
}
