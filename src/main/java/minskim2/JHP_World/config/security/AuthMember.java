package minskim2.JHP_World.config.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthMember implements Principal {
    private final Long id;
    private final String role;

    @Builder
    public AuthMember(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return List.of(authority);
    }
}
