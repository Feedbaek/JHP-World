package minskim2.JHP_World.slice;

import minskim2.JHP_World.domain.grade.controller.GradeController;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.service.GradeService;
import minskim2.JHP_World.global.dto.JsonBody;
import minskim2.JHP_World.global.enums.SuccessStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;
import static utils.TestUtility.MockKakaoUser;

@Transactional
@DisplayName("GradeController - 슬라이스 테스트")
public class GradeControllerTest {
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @TestConfiguration
//    static class Config {
//        @Bean
//        public GradeService gradeService() {
//            return new GradeService(null);
//        }
//    }
//
//    @Test
//    @DisplayName("과제 테스트 실행 - 실패")
//    void testGrade() {
//        GradeRequest gradeRequest = new GradeRequest();
//        gradeRequest.setSolutionId(1L);
//        gradeRequest.setTestCaseId(1L);
//        gradeRequest.setCode("code");
//
//        webTestClient
//                .mutateWith(csrf())
//                .mutateWith(mockOAuth2Login().oauth2User(MockKakaoUser()))
//                .post()
//                .uri("/api/grade")
//                .bodyValue(gradeRequest)
//                .exchange()
//                .expectStatus().isOk()  // TODO: 나중에는 실패해야 정상임
//                .expectBody(JsonBody.class).value(jsonBody -> {
//                    assertThat(jsonBody.getStatus()).isEqualTo(SuccessStatus.GRADE_SUCCESS.getStatus());
//                    assertThat(jsonBody.getMessage()).isEqualTo(SuccessStatus.GRADE_SUCCESS.getMessage());
//                    assertThat(jsonBody.getData()).isNotNull();
//                });
//    }
}
