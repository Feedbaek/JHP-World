package minskim2.JHP_World.domain.member.service;

import minskim2.JHP_World.domain.member.entity.Role;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.member.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import minskim2.JHP_World.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean isMemberAdmin(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        System.out.println(member.getRole().getName());
        return member.getRole().getName() == RoleName.ADMIN;
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
