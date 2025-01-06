package minskim2.JHP_World.router.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grade")
public class GradeController {
    private final GradeService gradeService;

    // 과제 테스트 실행
    @PostMapping
    public GradeResponse solutionGrade(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                                       @RequestBody @Valid GradeRequest gradeRequest) {
        return gradeService.solutionGrade(oAuth2User.getMemberId(), gradeRequest);
    }

    @PostMapping("/test")
    public GradeResponse testGrade2() {
        return gradeService.solutionGrade(1L, new GradeRequest());
    }
}
