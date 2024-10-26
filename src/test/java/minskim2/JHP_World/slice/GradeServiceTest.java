package minskim2.JHP_World.slice;

import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j(topic = "GradeServiceTest")
@DisplayName("GradeService - 슬라이스 테스트")
public class GradeServiceTest {

    private final GradeService gradeService;

    private final String CONTAINER_NAME = "cpp_runner_container";
    private final String COMPILE_EXECUTE_COMMAND = "g++ -xc++ - -o /usr/src/app/test.out";


    public GradeServiceTest() {
        gradeService = new GradeService(null);
    }

    @Test
    @DisplayName("C/C++ 코드 컴파일 및 실행 테스트 - 성공")
    void testGrade() {
        // given
        String code = "\n#include<iostream>\n" +
                "\n" +
                "int main() {\n" +
                "    std::cout << \"hello world!\" << std::endl;\n" +
                "    return 0;\n" +
                "}\n";

        // when
        GradeResponse result = gradeService.run(CONTAINER_NAME, COMPILE_EXECUTE_COMMAND, code);

        // then
        log.info("result: {}", result);
    }
}
