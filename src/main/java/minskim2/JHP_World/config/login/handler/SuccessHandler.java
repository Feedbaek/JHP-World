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
        logLogin(authentication.getName(), request);

        response.sendRedirect(redirect);
    }

    // 다음 필터 체이닝 호출 과정을 명시적으로 보이게 하기 위해 추가
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }

    public void logLogin(String username, HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        HttpSession session = request.getSession();

        // 세션 ID가 필요하다면
        String sessionId = session.getId();
        String oldSessionId = (String) session.getAttribute("OLD_SESSION_ID");

        // 로그인 정보 로깅
        // TODO: DB로 저장하는 로직 추가
        log.info("Login success. Username: {}, IP: {}, User-Agent: {}, Session ID: {}, Old Session ID: {}", username, ip, userAgent, sessionId, oldSessionId);
    }
}
