package minskim2.JHP_World.router.api;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-case")
public class TestCaseRestController {

    private final TestCaseService testCaseService;

    @GetMapping("")
    public List<TestCaseReq.Get> getTestCases(@RequestParam Long assignmentId,
                                          @Positive @RequestParam(defaultValue = "1", required = false) int page) {

        return testCaseService.findAllByAssignmentId(assignmentId, page - 1);
//        return List.of(new TestCaseReq.Get(1L, 1L, 1L, "input", "output", "description"));
    }
}
