package minskim2.JHP_World.domain.lecture.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.service.AssignmentService;
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
    public String assignmentList(Model model, @PathVariable Long lecture_id,
                                 @RequestParam(name = "page", defaultValue = "0", required = false) final int page) {

        var list = assignmentService.getAssignmentListByLectureId(lecture_id, page);
        model.addAttribute("assignmentList", list);
        model.addAttribute("title", "과제 목록");

        return "/pages/assignmentList";
    }
}
