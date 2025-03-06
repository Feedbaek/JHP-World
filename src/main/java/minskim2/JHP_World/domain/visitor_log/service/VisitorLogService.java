package minskim2.JHP_World.domain.visitor_log.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.visitor_log.entity.VisitorLog;
import minskim2.JHP_World.domain.visitor_log.repository.VisitorLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "VISITOR_LOG")
public class VisitorLogService {

    private final VisitorLogRepository visitorLogRepository;


    // 방문자 로그 기록
    public void visitLog(HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        // 방문자 로그 기록
        log.info("익명의 사용자 접속 > Anonymous IP: {}, User-Agent: {}, Session ID: {}", ip, userAgent, sessionId);
        logging(null, null, ip, userAgent, sessionId, null);
    }

    // 로그인 로그 기록
    public void loginLog(HttpServletRequest request, Authentication authentication) {

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        Member member = Member.ById(principal.getMemberId());
        String username = principal.getName();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String oldSessionId = (String) session.getAttribute("OLD_SESSION_ID");

        // 방문자 로그 기록
        log.info("사용자 로그인 > {} IP: {}, User-Agent: {}, Session ID: {}, Old Session ID: {}", username, ip, userAgent, sessionId, oldSessionId);
        logging(member, username, ip, userAgent, sessionId, oldSessionId);
    }


    // DB 저장
    private void logging(Member member, String username, String ip, String userAgent, String sessionId, String oldSessionId) {

        // 방문자 로그 저장
        VisitorLog visitorLog = VisitorLog.builder()
                .member(member)
                .userName(username)
                .ip(ip)
                .userAgent(userAgent)
                .sessionId(sessionId)
                .oldSessionId(oldSessionId)
                .build();

        visitorLogRepository.save(visitorLog);
    }
}
