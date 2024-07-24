package minskim2.JHP_World.domain.member.service;

import minskim2.JHP_World.domain.member.dto.MemberDto;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.exception.MemberException;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 멤버 엔티티 생성
    @Transactional
    public Member createMember(String phoneNumber, String kakaoId) throws MemberException {
        // todo: 중복 체크


        // todo: 멤버 생성
        Member member = Member.builder()
                .build();

        return memberRepository.save(member);
    }

    // 멤버 dto 생성
    @Transactional
    public MemberDto makeMemberDto(Member member) {
        // todo: 멤버 dto 생성
        return MemberDto.builder()
                .build();
    }
}
