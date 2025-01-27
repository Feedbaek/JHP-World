package minskim2.JHP_World.router.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grade")
public class GradeRestController {
    private final GradeService gradeService;


    // 과제 테스트 실행
    @PostMapping
    public GradeResponse solutionGrade(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                                       @RequestBody @Valid GradeRequest gradeRequest) {
        return gradeService.solutionGrade(oAuth2User.getMemberId(), gradeRequest);
    }

    @PostMapping("/test")
    public GradeResponse testGrade2(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @RequestBody GradeRequest.Test requestBody) {
        return gradeService.solutionGrade(oAuth2User.getMemberId(), requestBody);
    }
}
