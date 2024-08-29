package minskim2.JHP_World.integration;

import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.global.enums.SuccessStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestUtility.JsonRequest;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("GradeController - 통합 테스트")
public class GradeTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("과제 테스트 실행 - 실패")
    void testGrade() throws Exception {
        // Given
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setSolutionId(1L);
        gradeRequest.setTestCaseId(1L);
        gradeRequest.setCode("code");

        // when
        mockMvc.perform(JsonRequest(post("/api/grade"), gradeRequest))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SuccessStatus.GRADE_SUCCESS.getStatus()))
                .andExpect(jsonPath("$.message").value(SuccessStatus.GRADE_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
}
