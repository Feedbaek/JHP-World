package minskim2.JHP_World.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface Principal {
    Collection<? extends GrantedAuthority> getAuthorities();
}
