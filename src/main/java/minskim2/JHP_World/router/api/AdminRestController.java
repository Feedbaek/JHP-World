package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.member.dto.MemberReq;
import minskim2.JHP_World.domain.member.dto.MemberRes;
import minskim2.JHP_World.domain.member.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AssignmentService assignmentService;
    private final MemberService memberService;

    /**
     * 과제 생성
     * */
    @PostMapping("/assignment")
    public Long createAssignment(@Validated @RequestBody AssignmentReq.Create req) {
        return assignmentService.createAssignment(req);
    }

    /**
     * 과제 수정
     * */
    @PutMapping("/assignment")
    public Long updateAssignment(@Validated @RequestBody AssignmentReq.Update req) {
        return assignmentService.updateAssignment(req);
    }

    /**
     * 과제 삭제
     * */
    @DeleteMapping("/assignment")
    public Long deleteAssignment(@Validated @RequestBody AssignmentReq.Delete req) {
        return assignmentService.deleteAssignment(req);
    }

    /**
     * 사용자 정보 조회
     * */
    @GetMapping("/member")
    public MemberRes.AdminGet getMember(@RequestParam Long memberId) {
        return memberService.findById(memberId);
    }

    /**
     * 사용자 정보 수정
     * */
    @PatchMapping("/member")
    public Long updateMember(@Validated @RequestBody MemberReq.AdminUpdate req) {
        return memberService.updateMember(req);
    }

    /**
     * 사용자 정보 삭제
     * */
    @DeleteMapping("/member")
    public Long deleteMember(@RequestParam Long memberId) {
        return memberService.deleteMember(memberId);
    }
}
