package minskim2.JHP_World.config.login.defalult;

import lombok.*;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultUser implements UserDetails, CustomOAuth2User {

    private String Username;
    private String Password;
    private List<GrantedAuthority> authorities;

    @Override
    public String getOauth2Id() {
        return "kakao:adminOauth2id";
    }

    @Override
    public String getRegistrationId() {
        return "kakao";
    }

    @Override
    public Long getMemberId() {
        return 1L;
    }

    @Override
    public String getName() {
        return this.Username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
}
