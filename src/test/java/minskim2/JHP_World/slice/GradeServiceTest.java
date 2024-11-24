package minskim2.JHP_World.slice;

import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.service.GradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j(topic = "GradeServiceTest")
@DisplayName("GradeService - 슬라이스 테스트")
public class GradeServiceTest {

    private final GradeService gradeService;

    private final String COMPILE_COMMAND = "g++ -xc++ - -o /usr/src/app/output";
    private final String EXECUTE_COMMAND = "/usr/src/app/output";

    private final ArrayList<String> commandCompile = new ArrayList<>(List.of(COMPILE_COMMAND.split(" ")));
    private final ArrayList<String> commandExecute = new ArrayList<>(List.of(EXECUTE_COMMAND.split(" ")));


    public GradeServiceTest() {
        gradeService = new GradeService(null, null, null);
    }

    @Test
    @DisplayName("C/C++ 코드 컴파일 테스트 - 성공")
    void solutionGrade() {
        // given
        String code = "#include<iostream>\n" +
                "\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    std::cout << a + b;\n" +
                "    return 0;\n" +
                "}\n";

        // when
        String compileResult = gradeService.run(commandCompile, "2s", code);
        String executeResult = gradeService.run(commandExecute, "1s", "1 2");

        // then
        log.info("compile result: {}", compileResult);
        log.info("execute result: {}", executeResult);
    }

    @Test
    @DisplayName("C/C++ 무한 루프 코드 컴파일 및 실행 테스트 - 시간 초과로 강제 종료")
    void testInfiniteLoopGrade() {
        // given
        String code = "#include<iostream>\n" +
                "\n" +
                "int main() {\n" +
                "while (1);\n" +
                "    return 0;\n" +
                "}\n";

        // when
        String compileResult = gradeService.run(commandCompile, "2s", code);
        Throwable throwable = assertThrows(RuntimeException.class, () ->
                gradeService.run(commandExecute, "1s", "1 2")
        );

        // then
        log.info("compile result: {}", compileResult);
        log.info("execute result: {}", throwable.getMessage());
    }
}
