package minskim2.JHP_World.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "AuthenticationUtil")
public class AuthenticationUtil {

    @PreAuthorize("isAuthenticated()")
    public void checkLogin() {}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkAdmin() {}
}
