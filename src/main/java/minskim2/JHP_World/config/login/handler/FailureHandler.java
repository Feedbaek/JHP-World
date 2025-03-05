package minskim2.JHP_World.config.login.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.EnvBean;
import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * 로그인 실패 핸들러
 */
@Component
@RequiredArgsConstructor
public class FailureHandler implements AuthenticationFailureHandler {

    private final EnvBean envBean;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 로그인 실패시 예외처리
        response.sendRedirect(envBean.getLoginUrl() + "?error=" + URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8));
    }
}
