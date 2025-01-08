package minskim2.JHP_World.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

public class CustomSessionFixationProtectionStrategy extends SessionFixationProtectionStrategy {

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

        // 로그인 직전 세션
        HttpSession oldSession = request.getSession(false);
        String oldSessionId = null;
        if (oldSession != null) {
            oldSessionId = oldSession.getId();
        }

        // 부모 클래스에서 session fixation 보호 로직을 수행
        // (여기서 migrateSession()에 따라 새 세션이 발급됨)
        super.onAuthentication(authentication, request, response);

        // 부모 호출 후, 이제는 새 세션이 발급된 상태
        HttpSession newSession = request.getSession();
        if (oldSessionId != null) {
            // 새 세션에 구 세션 ID를 저장하여 "연결" 정보를 남긴다
            newSession.setAttribute("OLD_SESSION_ID", oldSessionId);
        }
    }
}
