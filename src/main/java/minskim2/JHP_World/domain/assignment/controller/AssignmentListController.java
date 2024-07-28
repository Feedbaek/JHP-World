package minskim2.JHP_World.domain.assignment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.common.ModelSetter;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignment/{lectureId}/list")
public class AssignmentListController {
    private final AssignmentService assignmentService;

    /**
     * 해당 강의 모든 과제 조회
     * */
    @GetMapping
    public String getAllList(@PathVariable Long lectureId, @RequestParam(defaultValue = "0") int page,
                             HttpServletRequest request, Model model) {
        // 현재 URI를 설정 && 페이징 처리
        ModelSetter.setCurrentUri(model, request.getRequestURI());
        ModelSetter.setPaging(model, page);

        // 해당 강의의 모든 과목 조회
        List<AssignmentDto> assignmentList = assignmentService.findAllByLectureId(lectureId, page);
        model.addAttribute("assignmentList", assignmentList);
        return "/pages/assignmentList";
    }
}
