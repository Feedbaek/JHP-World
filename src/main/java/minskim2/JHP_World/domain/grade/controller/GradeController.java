package minskim2.JHP_World.domain.grade.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import minskim2.JHP_World.global.dto.JsonBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static minskim2.JHP_World.global.enums.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grade")
public class GradeController {
    private final GradeService gradeService;

    // 과제 테스트 실행
    @PostMapping
    public GradeResponse testGrade(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @RequestBody GradeRequest gradeRequest) {
        return gradeService.testGrade(oAuth2User.getMemberId(), gradeRequest);
    }

    @PostMapping("/test")
    public JsonBody<GradeResponse> testGrade2() {
        GradeResponse gradeResponse = gradeService.testGrade(1L, new GradeRequest());
        return JsonBody.success(GRADE_SUCCESS, gradeResponse);
    }
}
