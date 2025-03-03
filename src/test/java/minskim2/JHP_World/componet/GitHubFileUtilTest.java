package minskim2.JHP_World.componet;

import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.global.utils.GitHubFileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j(topic = "GitHubFileUtilTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("GradeController - 통합 테스트")
public class GitHubFileUtilTest {

    @Autowired
    private GitHubFileUtil gitHubFileUtil;

    @Test
    @DisplayName("파일 업로드 테스트")
    void testUpload() throws IOException {
        // Given
         MultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "Test Data".getBytes());

        // When
         String filePath = gitHubFileUtil.upload(file, "application/pdf");

         log.info("filePath: {}", filePath);
        // Then
        // assertThat(filePath).isNotNull();
    }
}
