package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentRestController {

    private final TestCaseService testCaseService;


    @GetMapping("/{assignmentId}/test-cases")
    public List<TestCaseRes.Get> getTestCasesByAssigmentId(@PathVariable Long assignmentId, @PageParam int page) {

        return testCaseService.findAllByAssignmentId(assignmentId, page).toList();
    }
}
