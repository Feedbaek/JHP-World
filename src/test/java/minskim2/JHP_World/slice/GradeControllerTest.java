package minskim2.JHP_World.slice;

import org.junit.jupiter.api.DisplayName;
import org.springframework.transaction.annotation.Transactional;

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
