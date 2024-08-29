package minskim2.JHP_World.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.global.enums.SuccessStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestUtility.MockKakaoUser;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("GradeController - 통합 테스트")
public class GradeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("과제 테스트 실행 - 실패")
    void testGrade() throws Exception {
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setSolutionId(1L);
        gradeRequest.setTestCaseId(1L);
        gradeRequest.setCode("code");

        OAuth2User oAuth2User = MockKakaoUser();

        mockMvc.perform(
                        post("/api/grade")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(gradeRequest))
                                .with(csrf())
                                .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(oAuth2User))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SuccessStatus.GRADE_SUCCESS.getStatus()))
                .andExpect(jsonPath("$.message").value(SuccessStatus.GRADE_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
}
