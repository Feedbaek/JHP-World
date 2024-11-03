package minskim2.JHP_World.domain.grade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.repository.GradeRepository;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2(topic = "GradeService")
@RequiredArgsConstructor
public class GradeService {

    private final String COMPILE_COMMAND_STR = "g++ -xc++ - -o /usr/src/app/output";
    private final String EXECUTE_COMMAND_STR = "/usr/src/app/output";

    private final ArrayList<String> COMPILE_COMMAND = new ArrayList<>(List.of(COMPILE_COMMAND_STR.split(" ")));
    private final ArrayList<String> EXECUTE_COMMAND = new ArrayList<>(List.of(EXECUTE_COMMAND_STR.split(" ")));

    private final GradeRepository gradeRepository;
    private final TestCaseRepository testCaseRepository;


    @Transactional
    public GradeResponse testGrade(Long memberId, GradeRequest gradeRequest) {
        // TODO: 입력 값 검증
        // 컴파일 명령어 실행
        run(COMPILE_COMMAND, "2s", gradeRequest.getCode());
        // TODO: 테스트 케이스 가져오기
        TestCase testCase = testCaseRepository.findById(gradeRequest.getTestCaseId()).orElseThrow();
        // 실행 명령어 실행
        String executeResult = run(EXECUTE_COMMAND, "1s", testCase.getInput());

        // 실행 결과와 예상 결과 비교 후 결과 기록
        return getGradeResponseByResult(testCase.getOutput(), executeResult);
    }

    private static GradeResponse getGradeResponseByResult(String expectedOutput, String executeResult) {
        if (expectedOutput.equals(executeResult)) {
            // TODO: 테스트 성공 기록 저장
            return GradeResponse.builder()
                    .message("정답입니다.")
                    .result(executeResult)
                    .build();
        } else {
            // TODO: 테스트 실패 결과 저장
            return GradeResponse.builder()
                    .message("오답입니다.")
                    .result(executeResult)
                    .build();
        }
    }


    public String run(List<String> command, String timeout, String input) {

        StringBuilder output = new StringBuilder();

        // 명령어 실행 및 종료 코드 확인
        int exitCode = executeDockerCommand(command, timeout, input, output);

        if (exitCode == 0) {
            return output.toString();
        } else {
            log.error("명령어 실행 중 오류 발생: {}, {}", exitCode, output.toString());
            throw new RuntimeException("명령어 실행 실패");
        }
    }

    // Docker 명령어 실행 로직
    private int executeDockerCommand(List<String> command, String timeout, String input, StringBuilder output) {

        try {
            List<String> dockerCommand = new ArrayList<>(
                    List.of("docker", "exec", "-i", getContainerName(), "timeout", timeout));
            dockerCommand.addAll(command);

            // docker exec 명령어를 ProcessBuilder로 실행
            ProcessBuilder processBuilder = new ProcessBuilder(dockerCommand);
            // 프로세스 시작
            Process process = processBuilder.start();
            // 프로세스의 입력으로 문자열 전달
            try (OutputStream os = process.getOutputStream()) {
                os.write(input.getBytes());
                os.flush();
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int status = process.waitFor();

            if (status != 0) {
                log.error("Docker 명령어 실행 중 오류 발생: {}", output.toString());
                InputStream errorStream = process.getErrorStream();
                String errorOutput = new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
                log.error("Error output: {}", errorOutput);
            }
            return status;

        } catch (IOException | InterruptedException e) {
            log.error("Docker 명령어 실행 중 오류 발생: {}", e.getMessage(), e);
            return -1;  // 예외 발생 시 오류 코드 반환
        }
    }

    private String getContainerName() {
        // TODO: 컨테이너 이름을 동적으로 변경할 수 있도록 수정
        return "cpp_runner_container";
    }
}
