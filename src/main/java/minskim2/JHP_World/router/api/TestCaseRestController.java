package minskim2.JHP_World.router.api;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-case")
public class TestCaseRestController {

    private final TestCaseService testCaseService;

    @GetMapping("/{testCaseId}")
    public TestCaseRes.Get getTestCaseById(@PathVariable Long testCaseId) {

        return testCaseService.findById(testCaseId);
    }

    @PostMapping("")
    public TestCaseRes.Get createTestCase(@AuthenticationPrincipal CustomOAuth2User member, @Validated @RequestBody TestCaseReq.Create req) {

        return testCaseService.createTestCase(member.getMemberId(), req);
    }

    @PutMapping("")
    public TestCaseRes.Get updateTestCase(@AuthenticationPrincipal CustomOAuth2User member, @Validated @RequestBody TestCaseReq.Update req) {

        return testCaseService.updateTestCase(member.getMemberId(), req);
    }

    @DeleteMapping("")
    public void deleteTestCase(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam Long testCaseId) {

        testCaseService.deleteTestCase(member.getMemberId(), testCaseId);
    }
}
