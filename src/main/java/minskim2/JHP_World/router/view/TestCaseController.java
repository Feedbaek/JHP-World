package minskim2.JHP_World.router.view;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-case")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final GradeService gradeService;


    @GetMapping("/{testCaseId}")
    public String getTestCaseById(@PathVariable Long testCaseId, Model model) {

        TestCaseRes.Get testcase = testCaseService.findById(testCaseId);
        List<GradeResponse> recentGrades = gradeService.getRecentGradesByTestCaseId(testCaseId);
        model.addAttribute("testcase", testcase);
        model.addAttribute("recentGrades", recentGrades);

        return "pages/testcase";
    }
}
