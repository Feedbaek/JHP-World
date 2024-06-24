package minskim2.JHP_World.config.login.oauth2;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUser implements CustomOAuth2User {
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
    @Builder
    public KakaoUser(String registrationId, Long memberId, String name, String oauth2Id, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        this.registrationId = registrationId;
        this.memberId = memberId;
        this.name = name;
        this.oauth2Id = oauth2Id;
        this.attributes = attributes;
        this.authorities = authorities;
    }
}
