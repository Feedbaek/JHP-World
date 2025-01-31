package minskim2.JHP_World.config.login.defalult;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultLoginService implements UserDetailsService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Member admin = memberRepository.findByName(username).orElseThrow();

        if (!admin.getName().equals("admin")) {
            throw new IllegalArgumentException("해당 유저가 없습니다.");
        }

        return DefaultUser.builder()
                .Username(username)
                .Password(bCryptPasswordEncoder.encode("admin"))
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
    }
}
