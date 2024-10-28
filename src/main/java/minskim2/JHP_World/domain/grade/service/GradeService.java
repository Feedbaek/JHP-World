package minskim2.JHP_World.domain.grade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.repository.GradeRepository;
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

    private final String CONTAINER_NAME = "cpp_runner_container";

    private final GradeRepository gradeRepository;


    @Transactional
    public GradeResponse testGrade(Long memberId, GradeRequest gradeRequest) {
        // TODO: 입력 값 검증
        // TODO: 과제 테스트 실행로직 추가

        log.info("과제 테스트 실행");

        return GradeResponse.builder()
                .message("테스트 성공")
                .result("PASS")
                .build();
    }


    public GradeResponse run(List<String> command, String timeout, String input) {

        StringBuilder output = new StringBuilder();

        // 명령어 실행 및 종료 코드 확인
        int exitCode = executeDockerCommand(command, timeout, input, output);

        if (exitCode == 0) {
            return GradeResponse.builder()
                    .message("명령어 실행 성공")
                    .result(output.toString())
                    .build();
        } else if (exitCode == 1) {
            return GradeResponse.builder()
                    .message("명령어 실행 실패")
                    .result(output.toString())
                    .build();
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
            log.info("Docker 명령어 실행: {}", processBuilder.command());

            // 프로세스 시작
            Process process = processBuilder.start();
            log.info("Docker 명령어 실행 중...");

            // 프로세스의 입력으로 문자열 전달
            try (OutputStream os = process.getOutputStream()) {
                os.write(input.getBytes());
                os.flush();
            }
            log.info("Docker 명령어 입력: {}", input);

            // 프로세스의 출력을 읽어오기
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            // 프로세스가 종료될 때까지 대기 후 종료 코드 반환
            int status = process.waitFor();
            if (status == 0) {
                log.info("Docker 명령어 실행 완료: {}", output.toString());
            } else {
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
        return CONTAINER_NAME;
    }
}
