package minskim2.JHP_World.router.view;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentDto;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
import minskim2.JHP_World.global.utils.ModelSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final AssignmentService assignmentService;

    /**
     * 강의별 과제 목록 조회
     * */
    @GetMapping("/{lecture_id}/assignmentList")
    public String assignmentList(Model model, @PathVariable Long lecture_id, @Positive int page) {

        var assignmentPage = assignmentService.getAssignmentListByLectureId(lecture_id, page);
        var assignmentList = assignmentPage.map(AssignmentDto::from).toList();
        var totalPage = assignmentPage.getTotalPages();

        ModelSetter.init(model, "과제 목록", page, totalPage, "/lecture/" + lecture_id + "/assignmentList");
        model.addAttribute("assignmentList", assignmentList);

        return "/pages/assignmentList";
    }
}
