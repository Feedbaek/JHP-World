package minskim2.JHP_World.domain.member.service;

import minskim2.JHP_World.domain.member.dto.MemberRes;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.global.enums.SizeEnum.MEMBER_LIST;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 멤버 엔티티 생성
    @Transactional
    public Member createMember(String phoneNumber, String kakaoId) {
        // todo: 중복 체크


        // todo: 멤버 생성
        Member member = Member.builder()
                .build();

        return memberRepository.save(member);
    }

    // 멤버 dto 생성
    @Transactional
    public MemberRes.AdminGet makeMemberDto(Member member) {
        // todo: 멤버 dto 생성
        return MemberRes.AdminGet.builder()
                .build();
    }

    // 멤버 리스트 조회
    public Page<MemberRes.AdminGet> getMemberList(int page) {

        Pageable pageable = Pageable.ofSize(MEMBER_LIST.getSize()).withPage(page);

        return memberRepository.findAll(pageable)
                .map(MemberRes.AdminGet::from);
    }
}
