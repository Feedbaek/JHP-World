package minskim2.JHP_World.domain.assignment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.common.ModelSetter;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.service.LectureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static minskim2.JHP_World.global.constant.IntConstant.ASSIGNMENT_LIST_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final LectureService lectureService;

    /**
     * 특정 과제 내용 조회
     * */
    @GetMapping("/{assignmentId}")
    public String getAssignment(@PathVariable Long assignmentId, HttpServletRequest request, Model model) {
        // title 설정 && URI 설정
        AssignmentDto assignment = assignmentService.findById(assignmentId);
        Lecture lecture = lectureService.findById(assignment.getLectureId());
        ModelSetter.setTitle(model, lecture.getName(), "#" + assignment.getId());
        ModelSetter.setCurrentUri(model, request.getRequestURI());

        model.addAttribute("assignment", assignment);
        return "/pages/assignment";
    }
    /**
     * 특정 강의 모든 과제 조회
     * */
    @GetMapping("/{lectureId}/list")
    public String getAllList(@PathVariable Long lectureId, @Positive @RequestParam(name = "page", defaultValue = "1") final int positivePage,
                             HttpServletRequest request, Model model) {
        // title 설정 && URI 설정 && 페이징 처리
        Lecture lecture = lectureService.findById(lectureId);
        ModelSetter.setTitle(model, lecture.getName(), "과제 목록");
        ModelSetter.setCurrentUri(model, request.getRequestURI());
        ModelSetter.setPaging(model, positivePage);

        // 해당 강의의 모든 과목 조회
        List<AssignmentDto> assignmentList = assignmentService.getDtoListByLectureId(lectureId, positivePage - 1, ASSIGNMENT_LIST_SIZE);
        model.addAttribute("assignmentList", assignmentList);
        return "/pages/assignmentList";
    }
}
