package minskim2.JHP_World.domain.member.service;

import minskim2.JHP_World.domain.member.dto.MemberReq;
import minskim2.JHP_World.domain.member.dto.MemberRes;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.global.enums.SizeEnum.MEMBER_LIST;
import static minskim2.JHP_World.global.exception.ErrorCode.MEMBER_NOT_FOUND;

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

    // 관리자 멤버 조회
    public MemberRes.AdminGet findById(Long id) {
        return memberRepository.findById(id)
                .map(MemberRes.AdminGet::from)
                .orElseThrow(() -> CustomException.of(MEMBER_NOT_FOUND));
    }

    /**
     * 관리자 멤버 수정
     * */
    @Transactional
    public Long updateMember(MemberReq.AdminUpdate req) {
        // 멤버 조회
        Member member = memberRepository.findById(req.id())
                .orElseThrow(() -> CustomException.of(MEMBER_NOT_FOUND));
        //  사용자 정보 수정
        member.update(req);

        return member.getId();
    }

    /**
     * 관리자 멤버 삭제
     * */
    @Transactional
    public Long deleteMember(Long memberId) {
        // 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(MEMBER_NOT_FOUND));
        // 사용자 정보 삭제
        memberRepository.delete(member);

        return member.getId();
    }
}
