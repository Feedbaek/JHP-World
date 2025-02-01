package minskim2.JHP_World.domain.grade.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.global.enums.SizeEnum.GRADE_LIST;

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
     * 과제 테스트 결과 조회
     * */
    public GradeDto findById(Long id) {
        return GradeDto.from(gradeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 테스트 결과가 존재하지 않습니다.")
        ));
    }


    @Transactional
    public GradeDto save(Long assignmentId, Long solutionId, Long testCaseId) {
        Assignment assignment = Assignment.ById(assignmentId);
        Solution solution = Solution.ById(solutionId);
        TestCase testCase = TestCase.ById(testCaseId);

        Grade grade = Grade.builder()
                .assignment(assignment)
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
        GradeDto gradeDto = save(req.assignmentId(), solutionDto.id(), req.testCaseId());
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
        // Grade 업데이트
        update(subMessage.gradeId(), subMessage.success(), subMessage.result());
    }


    /**
     * 과제별 테스트 결과 조회
     * */
    public Page<GradeResponse> getGradeListByAssignmentId(Long assignmentId, int page) {
        return gradeRepository.findAllByAssignmentId(assignmentId, PageRequest.of(page, GRADE_LIST.getSize(), Sort.Direction.DESC, "createdDate"))
                .map(GradeResponse::from);
    }
}
