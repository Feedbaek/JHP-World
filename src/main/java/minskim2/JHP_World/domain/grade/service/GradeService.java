package minskim2.JHP_World.domain.grade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Transactional(readOnly = true)
@Log4j2(topic = "GradeService")
@RequiredArgsConstructor
public class GradeService {
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


    public GradeResponse run(String containerName, String command) {

        StringBuilder output = new StringBuilder();
        // 명령어 실행 및 종료 코드 확인
        int exitCode = executeDockerCommand(containerName, command, output);

        if (exitCode == 0) {
            log.info("명령어가 성공적으로 실행되었습니다.");
            return GradeResponse.builder()
                    .message("명령어 실행 성공")
                    .result(output.toString())
                    .build();
        } else if (exitCode == 1) {
            log.error("명령어 실행 중 오류 발생: {}", output.toString());
            return GradeResponse.builder()
                    .message("명령어 실행 실패")
                    .result(output.toString())
                    .build();
        } else {
            throw new RuntimeException("명령어 실행 실패");
        }
    }

    // Docker 명령어 실행 로직
    public int executeDockerCommand(String containerName, String command, StringBuilder output) {

        try {
            // docker exec 명령어를 ProcessBuilder로 실행
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "exec", containerName, "sh", "-c", command
            );

            // 프로세스 시작
            Process process = processBuilder.start();

            // 프로세스의 출력을 읽어오기
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // 프로세스가 종료될 때까지 대기 후 종료 코드 반환
            return process.waitFor();

        } catch (IOException | InterruptedException e) {
            log.error("Docker 명령어 실행 중 오류 발생: {}", e.getMessage(), e);
            return -1;  // 예외 발생 시 오류 코드 반환
        }
    }
}
