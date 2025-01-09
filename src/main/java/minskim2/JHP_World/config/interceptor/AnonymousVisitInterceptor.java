package minskim2.JHP_World.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.visitor_log.service.VisitorCountService;
import minskim2.JHP_World.domain.visitor_log.service.VisitorLogService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j(topic = "ANONYMOUS_VISIT")
@RequiredArgsConstructor
public class AnonymousVisitInterceptor implements HandlerInterceptor {

    private final VisitorLogService visitorLogService;
    private final VisitorCountService visitorCountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // true 넣으면 세션이 없으면 새로 생성
        HttpSession session = request.getSession(true);

        // 세션이 새로 만들어졌다면 isNew()가 true
        if (session.isNew()) {
            // 익명 사용자 최초 방문에 대한 로그 기록
            visitorLogService.visitLog(request);
            // 방문자 수 증가
            visitorCountService.updateTodayVisitors();
            visitorCountService.updateTotalVisitors();
        }

        return true;  // 다음 단계(컨트롤러)로 진행
    }
}
