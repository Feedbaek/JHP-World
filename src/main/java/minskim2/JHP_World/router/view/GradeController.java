package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import minskim2.JHP_World.global.utils.ModelSetter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/grade")
@Slf4j(topic = "GradeController")
public class GradeController {

    private final GradeService gradeService;

    // 과제 테스트 결과 조회
    @GetMapping("/result")
    public String getGradeResult(@RequestParam Long assignmentId, Model model, @PageParam int page) {

        Page<GradeResponse> gradeList = gradeService.getGradeListByAssignmentId(assignmentId, page);
        ModelSetter.init(model, "과제 테스트 결과 조회", page, gradeList.getTotalPages(), "/grade/result?assignmentId=" + assignmentId);

        model.addAttribute("gradeList", gradeList);
        return "pages/gradingResult";
    }
}
