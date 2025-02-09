package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-case")
public class TestCaseRestController {

    private final TestCaseService testCaseService;

    @GetMapping("")
    public List<TestCaseRes.Get> getTestCases(@RequestParam Long assignmentId, @PageParam int page) {

        return testCaseService.findAllByAssignmentId(assignmentId, page).toList();
//        return List.of(new TestCaseReq.Get(1L, 1L, 1L, "input", "output", "description"));
    }
}
