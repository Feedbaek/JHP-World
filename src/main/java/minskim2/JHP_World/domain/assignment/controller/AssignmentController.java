package minskim2.JHP_World.domain.assignment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.common.ModelSetter;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.dto.LectureDto;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import minskim2.JHP_World.domain.post.dto.PostDto;
import minskim2.JHP_World.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static minskim2.JHP_World.global.constant.IntConstant.ASSIGNMENT_LIST_SIZE;
import static minskim2.JHP_World.global.constant.IntConstant.PREVIEW_SIZE;

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
        // title 설정 && URI 설정
        AssignmentDto assignmentDto = assignmentService.findById(assignmentId);
        ModelSetter.setTitle(model, assignmentDto.getTitle(), "#" + assignmentDto.getId());
        ModelSetter.setCurrentUri(model, request.getRequestURI());

        // 해당 강의 게시물 조회
        List<PostDto> postDto = postService.findAllByLectureId(assignmentId, 0, PREVIEW_SIZE);

        // model에 추가
        model.addAttribute("assignment", assignmentDto);
        model.addAttribute("postList", postDto);
        return "/pages/assignment";
    }


    /**
     * @deprecated
     * 특정 강의 모든 과제 조회
     * */
    @GetMapping("/list/lecture/{lectureId}")
    public String getAllList(@PathVariable Long lectureId, @Positive @RequestParam(name = "page", defaultValue = "1", required = false) final int positivePage,
                             HttpServletRequest request, Model model) {
        // title 설정 && URI 설정 && 페이징 처리
        LectureDto lectureDto = lectureService.findById(lectureId);
        ModelSetter.setTitle(model, lectureDto.getName(), "과제 목록");
        ModelSetter.setCurrentUri(model, request.getRequestURI());
        ModelSetter.setPaging(model, positivePage);

        // 해당 강의의 모든 과목 조회
        List<AssignmentDto> assignmentList = assignmentService.getDtoListByLectureId(lectureId, positivePage - 1, ASSIGNMENT_LIST_SIZE);
        model.addAttribute("assignmentList", assignmentList);
        return "/pages/assignmentList";
    }
}
