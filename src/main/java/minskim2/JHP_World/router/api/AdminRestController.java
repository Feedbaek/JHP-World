package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.defalult.DefaultUser;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.dto.AssignmentReq;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.member.dto.MemberReq;
import minskim2.JHP_World.domain.member.dto.MemberRes;
import minskim2.JHP_World.domain.member.service.MemberService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
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
    private final PostService postService;
    private final TestCaseService testCaseService;

    /**
     * 과제 조회
     * */
    @GetMapping("/assignment")
    public AssignmentDto getAssignment(@RequestParam Long assignmentId) {
        return assignmentService.findById(assignmentId);
    }

    /**
     * 과제 생성
     * */
    @PostMapping("/assignment")
    public Long createAssignment(@Validated @ModelAttribute AssignmentReq.Create req) {
        return assignmentService.createAssignment(req);
    }

    /**
     * 과제 수정
     * */
    @PatchMapping("/assignment")
    public Long updateAssignment(@Validated @RequestBody AssignmentReq.Update req) {
        return assignmentService.updateAssignment(req);
    }

    /**
     * 과제 삭제
     * */
    @DeleteMapping("/assignment")
    public Long deleteAssignment(@RequestParam Long assignmentId) {
        return assignmentService.deleteAssignment(assignmentId);
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

    /**
     * 토론글 정보 조회
     * */
    @GetMapping("/post")
    public PostRes.GetPreviewRes getPost(@RequestParam Long postId) {
        return postService.findById(postId);
    }

    /**
     * 테스트 케이스 조회
     * */
    @GetMapping("/test-case")
    public TestCaseRes.Get getTestCase(@RequestParam Long testCaseId) {
        return testCaseService.findById(testCaseId);
    }

    /**
     * 테스트 케이스 생성
     * */
    @PostMapping("/test-case")
    public TestCaseRes.Get createTestCase(@AuthenticationPrincipal DefaultUser admin,
                                          @Validated @RequestBody TestCaseReq.CreateByAdmin req) {
        return testCaseService.createTestCaseByAdmin(admin.getMemberId(), req);
    }

    /**
     * 테스트 케이스 수정
     * */
    @PatchMapping("/test-case")
    public TestCaseRes.Get updateTestCase(@AuthenticationPrincipal DefaultUser admin,
                                          @Validated @RequestBody TestCaseReq.UpdateByAdmin req) {
        return testCaseService.updateTestCase(admin.getMemberId(), req);
    }
}
