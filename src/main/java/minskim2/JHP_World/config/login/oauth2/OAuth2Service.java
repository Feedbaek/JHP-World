package minskim2.JHP_World.config.login.oauth2;

import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.entity.Role;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j(topic = "OAuth2UserService")
@RequiredArgsConstructor
@Transactional
@Service
public class OAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        return saveOrUpdate(oAuth2User, registrationId);
    }

    private OAuth2User saveOrUpdate(OAuth2User oAuth2User, String registrationId) {
        return switch (registrationId) {  // 나중에 다른 OAuth2 공급자 추가 가능
            case "kakao" -> saveOrUpdateKakaoUser(oAuth2User);
            default -> throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 공급자입니다.");
        };
    }

    private OAuth2User saveOrUpdateKakaoUser(OAuth2User oAuth2User) {
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String name = profile.get("nickname").toString();

        Member member = findOrSaveMember(oAuth2User, "kakao", name);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().getRoleName().name()));

        return KakaoUser.builder()
                .registrationId("kakao")
                .memberId(member.getId())
                .oauth2Id(member.getOauth2id())
                .name(name)
                .attributes(oAuth2User.getAttributes())
                .authorities(authorities)
                .build();
    }

    private Member findOrSaveMember(OAuth2User oAuth2User, String registrationId, String name) {
        String oauth2Id = registrationId + ":" + oAuth2User.getName();
        // 임시 유저로 역할 설정
        Role role = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> CustomException.of(ErrorCode.ROLE_NOT_FOUND));
        // todo: member 엔티티 확정 후 수정
        return memberRepository.findByOauth2id(oauth2Id)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .oauth2id(oauth2Id)
                        .name(name)
                        .role(role)
                        .isEnabled(true)
                        .build()));
    }
}
