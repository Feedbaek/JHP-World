package minskim2.JHP_World.domain.grade.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.grade.dto.ExecuteRequest;
import minskim2.JHP_World.domain.grade.dto.GradeDto;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.entity.Grade;
import minskim2.JHP_World.domain.grade.repository.GradeRepository;
import minskim2.JHP_World.domain.solution.dto.SolutionDto;
import minskim2.JHP_World.domain.solution.entity.Solution;
import minskim2.JHP_World.domain.solution.service.SolutionService;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.service.TestCaseService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2(topic = "GradeService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

    private final GradeRepository gradeRepository;
    private final SolutionService solutionService;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;
    private final TestCaseService testCaseService;

    @Value("${spring.rabbitmq.pub-exchange}")
    private String pubExchange;
    @Value("${spring.rabbitmq.pub-routing-key}")
    private String pubRoutingKey;
    @Value("${spring.rabbitmq.sub-queue}")
    private String subQueue;

    /**
     * 과제 테스트 실행
     * @param memberId 사용자 ID
     * @param gradeRequest 테스트 요청 정보
     * @return 테스트 결과
     * </p>
     * 로그인한 사용자의 과제 테스트를 실행한다. 사용자는 이미 만들어져 있는 테스트 케이스와 솔루션을 선택하여 테스트를 실행할 수 있다.
     * 테스트 케이스는 자유롭게 선택할 수 있으나, 솔루션은 로그인한 사용자가 작성한 솔루션만 선택할 수 있다.
     * 테스트 케이스의 입력값을 솔루션의 소스코드에 적용하여 실행한 결과를 저장하고 반환한다.
     * */
    @Transactional
    public GradeResponse solutionGrade(Long memberId, GradeRequest gradeRequest) {
        // TODO: 입력 값 검증
        // validateRequest(gradeRequest);\

        // Test Case와 Solution 조회
//        TestCase testCase = testCaseRepository.findById(gradeRequest.getTestCaseId()).orElseThrow();
//        Solution solution = solutionRepository.findByIdAndMemberId(gradeRequest.getSolutionId(), memberId).orElseThrow();

        // TODO: 컴파일 및 실행 결과 로직 분리 및 수정
        // 컴파일 명령어 실행
//        run(COMPILE_COMMAND, "2s", solution.getSourceCode());

        // 실행 명령어 실행
//        String executeResult = run(EXECUTE_COMMAND, "1s", testCase.getInput());
//        rabbitTemplate.convertAndSend("jhp-exchange", "grading",
//                "#include<iostream>\n" +
//                "using namespace std;\n" +
//                "int main() {\n" +
//                "    int a, b;\n" +
//                "    cin >> a >> b;\n" +
//                "    cout << a + b;\n" +
//                "    return 0;\n" +
//                "}");
        rabbitTemplate.convertAndSend(pubExchange, pubRoutingKey, gradeRequest);

        // 결과 비교 후 저장 및 응답 반환
//        return saveGradeAndGetResponse(testCase, solution, "SUCCESS");
        return null;
    }

    /**
     * 과제 테스트 결과 조회
     * */
    public GradeDto findById(Long id) {
        return GradeDto.from(gradeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 테스트 결과가 존재하지 않습니다.")
        ));
    }


    @Transactional
    public GradeDto save(Long solutionId, Long testCaseId) {
        Solution solution = Solution.ById(solutionId);
        TestCase testCase = TestCase.ById(testCaseId);

        Grade grade = Grade.builder()
                .solution(solution)
                .testCase(testCase)
                .build();

        return GradeDto.from(gradeRepository.save(grade));
    }

    /**
     * 과제 테스트 결과 업데이트
     * */
    @Transactional
    public void update(Long id, Boolean success, String result) {

        Grade grade = gradeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 테스트 결과가 존재하지 않습니다.")
        );
        grade.update(success, result);

        gradeRepository.save(grade);
    }

    /**
     * 과제 테스트 실행
     * RabbitMQ에 메시지 전송
     * */
    @Transactional
    public void solutionGrade(Long memberId, GradeRequest.Test req) {

        // Solution 생성
        SolutionDto solutionDto = solutionService.save(memberId, req.assignmentId(), req.code());
        // Grade 생성
        GradeDto gradeDto = save(solutionDto.id(), req.testCaseId());
        // Test Case 조회
        TestCaseReq.Get testCase = testCaseService.findById(req.testCaseId());

        // RabbitMQ에 전송할 메시지
        ExecuteRequest.PubMessage pubMessage = ExecuteRequest.PubMessage.builder()
                .gradeId(gradeDto.id())
                .input(testCase.input())
                .output(testCase.output())
                .code(req.code())
                .build();

        // RabbitMQ에 메시지 전송
        rabbitTemplate.convertAndSend(pubExchange, pubRoutingKey, pubMessage);
    }

    @Transactional
    @RabbitListener(queues = "${spring.rabbitmq.sub-queue}")
    public void receiveMessage(ExecuteRequest.SubMessage subMessage) {
        log.info("<Received message>\n{}", subMessage.result());

        // Grade 업데이트
        update(subMessage.gradeId(), subMessage.success(), subMessage.result());
    }



    /**
     * 테스트 결과 저장 및 반환
     * */
    private GradeResponse saveGradeAndGetResponse(TestCase testCase, Solution solution, String executeResult) {

        Grade grade = Grade.builder()
                .testCase(testCase)
                .solution(solution)
                .result(executeResult)
                .success(executeResult.equals(testCase.getOutput()))  // 결과 비교 후 메시지 설정
                .build();

        gradeRepository.save(grade);

        return GradeResponse.from(grade);
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
