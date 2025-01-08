package minskim2.JHP_World.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j(topic = "ANONYMOUS_VISIT")
public class AnonymousVisitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // true 넣으면 세션이 없으면 새로 생성
        HttpSession session = request.getSession(true);

        // 세션이 새로 만들어졌다면 isNew()가 true
        if (session.isNew()) {
            String newSessionId = session.getId();
            // 익명 사용자 최초 방문에 대한 로그 기록
            logAnonymousVisit(newSessionId, request);
        }

        return true;  // 다음 단계(컨트롤러)로 진행
    }

    private void logAnonymousVisit(String sessionId, HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        // 원하는 정보를 추가로 수집
        // TODO: DB로 저장하는 로직 추가
        log.info("New anonymous visit. Session ID: {}, IP: {}, User-Agent: {}", sessionId, ip, userAgent);
    }
}
