package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.member.dto.MemberRes;
import minskim2.JHP_World.domain.member.service.MemberService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import minskim2.JHP_World.global.utils.ModelSetter;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.POST_LIST_DEFAULT;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final TestCaseService testCaseService;
    private final PostService postService;

    /**
     * 관리자 페이지
     * */
    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHomePage() {
        return "pages/admin/home";
    }

    /**
     * 관리자 로그인 페이지
     * */
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "pages/admin/login";
    }

    /**
     * 관리자의 사용자 관리 페이지
     * */
    @GetMapping("/member")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditPage(Model model, @PageParam int page) {

        Page<MemberRes.AdminGet> memberList = memberService.getMemberList(page);
        model.addAttribute("memberList", memberList);
        ModelSetter.init(model, "회원 관리", page, memberList.getTotalPages(), "/admin/member");

        return "pages/admin/member";
    }

    /**
     * 관리자의 과제 관리 페이지
     * */
    @GetMapping("/assignment")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAssignmentPage(Model model, @PageParam int page, @RequestParam(required = false) Long lectureId) {
        // 과제 목록 조회
        Integer totalPage = null;
        if (lectureId != null) {
            Page<AssignmentDto> assignmentList = assignmentService.getDtoListByLectureId(lectureId, page);
            model.addAttribute("assignmentList", assignmentList);
            totalPage = assignmentList.getTotalPages();
        }

        // 강의 목록 조회
        List<LectureDto> lectureList = lectureService.findAll();
        model.addAttribute("lectureList", lectureList);

        ModelSetter.init(model, "과제 관리", page, totalPage, "/admin/assignment");

        return "pages/admin/assignment";
    }

    /**
     * 관리자의 테스트 케이스 관리 페이지
     * */
    @GetMapping("/testcase")
    @PreAuthorize("hasRole('ADMIN')")
    public String getTestCasePage(Model model, @PageParam int page, @RequestParam Long assignmentId) {
        // 테스트 케이스 조회
        Integer totalPage = null;
        if (assignmentId != null) {
            Page<TestCaseRes.Get> testcaseList = testCaseService.findAllByAssignmentId(assignmentId, page);
            model.addAttribute("testcaseList", testcaseList);
            totalPage = testcaseList.getTotalPages();
        }

        ModelSetter.init(model, "테스트 케이스 관리", page, totalPage, "/admin/testcase");

        return "pages/admin/testcase";
    }

    /**
     * 관리자의 토론글 관리 페이지
     * */
    @GetMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPostPage(Model model, @PageParam int page, @RequestParam(required = false) Long lectureId) {
        // 토론글 목록 조회
        Integer totalPage = null;
        if (lectureId != null) {
            Page<PostRes.GetPreviewRes> postList = postService.findAllByLectureId(lectureId, page, POST_LIST_DEFAULT.getSize());
            model.addAttribute("postList", postList);
            totalPage = postList.getTotalPages();
        }

        // 강의 목록 조회
        List<LectureDto> lectureList = lectureService.findAll();
        model.addAttribute("lectureList", lectureList);

        ModelSetter.init(model, "토론글 관리", page, totalPage, "/admin/post");

        return "pages/admin/post";
    }
}
