package minskim2.JHP_World.router.api;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.service.GradeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grade")
public class GradeRestController {
    private final GradeService gradeService;


    // 과제 테스트 실행
    @PostMapping("/test")
    public void testGrade(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                                    @RequestBody @Valid GradeRequest.Test requestBody) {
        gradeService.solutionGrade(oAuth2User.getMemberId(), requestBody);
    }
}
