package minskim2.JHP_World.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.login.defalult.DefaultUser;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.member.service.RoleService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import java.security.Principal;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationIntercepter implements HandlerInterceptor {


    private final RoleService roleService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // 로그인 사용자 확인
        Principal principal = request.getUserPrincipal();
        CustomOAuth2User member = null;

        // 로그인 사용자인 경우
        if (principal != null) {
            // OAuth2 로그인 사용자
            if (principal instanceof OAuth2AuthenticationToken token) {
                member = (CustomOAuth2User) token.getPrincipal();
            } // 일반 로그인 사용자
            else if (principal instanceof UsernamePasswordAuthenticationToken token) {
                member = (DefaultUser) token.getPrincipal();
            } // 로그인 사용자가 아닌 경우
            else {
                return false;
            }
        }
        // 사용자 ID 가져오기
        Long userId = member.getMemberId(); // Spring Security로 로그인된 사용자
//        log.info("member = {}",member);

        // Role 확인하기
        boolean isAdmin = roleService.isMemberAdmin(userId);
//        log.info("is Admin = {}", isAdmin);
        return isAdmin;
    }
}
