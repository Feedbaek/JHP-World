package minskim2.JHP_World.domain.member.service;

import minskim2.JHP_World.domain.member.entity.Role;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.member.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import minskim2.JHP_World.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    public boolean isMemberAdmin(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getRole().equals(RoleName.ADMIN);
    }

    // RoleName enum에 있는 모든 RoleName으로 Role을 생성
    @PostConstruct
    protected void init() {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(Role.builder()
                        .name(roleName).build()
                );
            }
        }
    }
}
