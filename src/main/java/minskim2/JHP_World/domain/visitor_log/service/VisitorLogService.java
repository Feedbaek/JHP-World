package minskim2.JHP_World.domain.visitor_log.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.visitor_log.entity.VisitorLog;
import minskim2.JHP_World.domain.visitor_log.repository.VisitorLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "VISITOR_LOG")
public class VisitorLogService {

    private final VisitorLogRepository visitorLogRepository;

    public void visitLog(HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        // 방문자 로그 기록
        log.info("익명의 사용자 접속 > Anonymous IP: {}, User-Agent: {}, Session ID: {}", ip, userAgent, sessionId);
        logging(null, ip, userAgent, sessionId, null);
    }

    public void loginLog(HttpServletRequest request, String username) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String oldSessionId = (String) session.getAttribute("OLD_SESSION_ID");

        // 방문자 로그 기록
        log.info("사용자 로그인 > {} IP: {}, User-Agent: {}, Session ID: {}, Old Session ID: {}", username, ip, userAgent, sessionId, oldSessionId);
        logging(username, ip, userAgent, sessionId, oldSessionId);
    }


    private void logging(String username, String ip, String userAgent, String sessionId, String oldSessionId) {

        // 방문자 로그 저장
        VisitorLog visitorLog = VisitorLog.builder()
                .userName(username)
                .ip(ip)
                .userAgent(userAgent)
                .sessionId(sessionId)
                .oldSessionId(oldSessionId)
                .build();

        visitorLogRepository.save(visitorLog);
    }
}
