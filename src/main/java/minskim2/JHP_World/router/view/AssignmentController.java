package minskim2.JHP_World.router.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.global.utils.ModelSetter;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.dto.PostRes;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static minskim2.JHP_World.global.enums.SizeEnum.ASSIGNMENT_LIST;
import static minskim2.JHP_World.global.enums.SizeEnum.DEFAULT_PREVIEW;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final PostService postService;

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
        var postPage = postService.findAllByLectureId(assignmentId, 1, DEFAULT_PREVIEW.getSize());
        var postList = postPage.map(PostRes.GetPreviewRes::from).toList();

        // model에 추가
        model.addAttribute("assignment", assignmentDto);
        model.addAttribute("postList", postList);
        return "/pages/assignment";
    }


    /**
     * 특정 강의의 모든 과제 조회
     * */
    @GetMapping("/list")
    public String getAllList(@RequestParam Long lectureId,
            @Positive @RequestParam(defaultValue = "1", required = false) int page,
            HttpServletRequest request, Model model) {

        // 해당 강의 조회
        LectureDto lectureDto = lectureService.findById(lectureId);

        // 해당 강의의 모든 과목 조회
        var assignmentPage = assignmentService.getDtoListByLectureId(lectureId, page, ASSIGNMENT_LIST.getSize());
        var assignmentList = assignmentPage.map(AssignmentDto::from);
        var totalPages = assignmentPage.getTotalPages();
        var currentUri = request.getRequestURI();

        // model에 추가
        ModelSetter.init(model, lectureDto.getName() + " 과제 목록", page, totalPages, currentUri);
        model.addAttribute("assignmentList", assignmentList);

        return "/pages/assignmentList";
    }
}
