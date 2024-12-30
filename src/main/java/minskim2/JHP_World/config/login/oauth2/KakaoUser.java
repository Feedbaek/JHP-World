package minskim2.JHP_World.config.login.oauth2;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUser implements CustomOAuth2User, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String registrationId;  // kakao
    private Long memberId;  // DB에 저장된 id
    private String name;  // kakao 닉네임
    private String oauth2Id;  // kakaoId
    private Map<String, Object> attributes;  // kakao 정보
    private Collection<? extends GrantedAuthority> authorities;  // 권한

    @Override
    public Long getMemberId() {
        return memberId;
    }
    @Override
    public String getOauth2Id() {
        return oauth2Id;
    }
    @Override
    public String getRegistrationId() {
        return registrationId;
    }
}
