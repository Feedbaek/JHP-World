package minskim2.JHP_World.config.login.handler;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.EnvBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.notification.service.NotificationService;
import minskim2.JHP_World.domain.visitor_log.service.VisitorLogService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "SuccessHandler")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessHandler implements AuthenticationSuccessHandler {

    private final EnvBean envBean;
    // Spring Security에서는 RequestCache를 사용하여 로그인 전에 기존 요청을 저장한다.
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final VisitorLogService visitorLogService;
    private final NotificationService notificationService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 로그인 성공 시 리다이렉트
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirect;
        if (savedRequest == null) {
            // 기존 요청이 없을 경우 기본 리다이렉트
            redirect = envBean.getLoginSuccessUrl();
        } else {
            // 기존 요청이 있을 경우 해당 경로로 리다이렉트
            redirect = savedRequest.getRedirectUrl();
        }
        // 로그인 성공 로깅
        visitorLogService.loginLog(request, authentication);
        // 알림 확인
        checkNotification(request, authentication);

        response.sendRedirect(redirect);
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
