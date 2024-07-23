package minskim2.JHP_World.config.login.handler;

import minskim2.JHP_World.config.EnvBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessHandler implements AuthenticationSuccessHandler {
    private final EnvBean envBean;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 리다이렉트
        response.sendRedirect(envBean.getLoginSuccessUrl());
    }
    // 다음 필터 체이닝 호출 과정을 명시적으로 보이게 하기 위해 추가
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }
}
