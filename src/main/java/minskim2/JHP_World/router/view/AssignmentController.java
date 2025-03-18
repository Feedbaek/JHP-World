package minskim2.JHP_World.router.view;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import minskim2.JHP_World.global.utils.ModelSetter;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static minskim2.JHP_World.global.enums.SizeEnum.*;

@Controller
@Slf4j(topic = "AssignmentController")
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final PostService postService;
    private final TestCaseService testCaseService;

    /**
     * 특정 과제 내용 조회
     * */
    @GetMapping("/{assignmentId}")
    public String getAssignment(@PathVariable Long assignmentId, HttpServletRequest request, Model model) {

        AssignmentDto assignmentDto = assignmentService.findById(assignmentId);
        // title 설정 && URI 설정
        ModelSetter.setTitle(model, assignmentDto.getTitle());
        ModelSetter.setCurrentUri(model, request.getRequestURI());

        // 해당 강의 게시물 조회
        Page<PostRes. GetPreviewRes> postList = postService.findAllByLectureId(assignmentDto.getLectureId(), 0, POST_LIST_PREVIEW.getSize());

        // model에 추가
        model.addAttribute("assignment", assignmentDto);
        model.addAttribute("postList", postList);
        return "/pages/assignment";
    }


    /**
     * 특정 강의의 모든 과제 조회
     * */
    @GetMapping("/list")
    public String getAllList(@RequestParam Long lectureId, @PageParam int page,
            HttpServletRequest request, Model model) {

        // 해당 강의 조회
        LectureDto lectureDto = lectureService.findById(lectureId);

        // 해당 강의의 모든 과목 조회
        Page<AssignmentDto> assignmentList = assignmentService.getDtoListByLectureId(lectureId, page, ASSIGNMENT_LIST.getSize());
        int totalPages = assignmentList.getTotalPages();
        String currentUri = request.getRequestURI();

        // model에 추가
        ModelSetter.init(model, lectureDto.getName() + " 과제 목록", page, totalPages, currentUri);
        model.addAttribute("assignmentList", assignmentList);

        return "/pages/assignmentList";
    }

    /**
     * 과제 제출 페이지
     * */
    @GetMapping("/submit")
    public String submitAssignment(@RequestParam Long assignmentId, Model model) {

        model.addAttribute("assignmentId", assignmentId);
        return "/pages/submitAssignment";
    }

    /**
     * 과제 id 기반으로 테스트 케이스 조회
     * */
    @GetMapping("/{assignmentId}/test-cases")
    public String getTestCase(@PathVariable Long assignmentId, @PageParam int page, Model model) {

        // 테스트 케이스 조회
        Page<TestCaseRes.Get> testcaseList = testCaseService.findPublicByAssignmentId(assignmentId, page);
        int totalPages = testcaseList.getTotalPages();

        // model에 추가
        ModelSetter.init(model, "테스트 케이스 관리", page, totalPages, "/assignment/" + assignmentId + "/test-cases");
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("testcaseList", testcaseList);

        return "pages/testcaseList";
    }
}
