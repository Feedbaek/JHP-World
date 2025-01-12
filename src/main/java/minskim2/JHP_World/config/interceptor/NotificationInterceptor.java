package minskim2.JHP_World.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.config.login.defalult.DefaultUser;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
        AbstractAuthenticationToken token = (AbstractAuthenticationToken) request.getUserPrincipal();

        // 로그인 사용자인 경우
        if (token != null) {
            // OAuth2 로그인 사용자와 일반 로그인 사용자
            CustomOAuth2User member = (CustomOAuth2User) token.getPrincipal();

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
