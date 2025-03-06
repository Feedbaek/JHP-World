package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    }

    @PostMapping("")
    public TestCaseRes.Get createTestCase(@AuthenticationPrincipal CustomOAuth2User member, @RequestBody TestCaseReq.Create req) {

        return testCaseService.createTestCase(member.getMemberId(), req);
    }
}
