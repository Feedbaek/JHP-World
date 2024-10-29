package minskim2.JHP_World.config.login.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 로그인 실패 핸들러
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 로그인 실패시 예외처리
        throw CustomException.of(ErrorCode.LOGIN_FAILED);
    }
}
