package minskim2.JHP_World.domain.grade.controller;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import minskim2.JHP_World.global.dto.JsonBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static minskim2.JHP_World.global.enums.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grade")
public class GradeController {
    private final GradeService gradeService;

    // 과제 테스트 실행
    @PostMapping
    public JsonBody<GradeResponse> testGrade(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @RequestBody GradeRequest gradeRequest) {
        GradeResponse gradeResponse = gradeService.testGrade(oAuth2User.getMemberId(), gradeRequest);
        return JsonBody.success(GRADE_SUCCESS, gradeResponse);
    }
}
