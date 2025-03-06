package minskim2.JHP_World.domain.test_case.service;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.global.enums.SizeEnum.TEST_CASE_LIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    public TestCaseRes.Get findById(Long id) {
        return TestCaseRes.Get.of(testCaseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 테스트 케이스가 존재하지 않습니다.")
        ));
    }

    public Page<TestCaseRes.Get> findAllByAssignmentId(Long assignmentId, int page) {
        return testCaseRepository.findAllByAssignmentId(assignmentId, Pageable.ofSize(TEST_CASE_LIST.getSize()).withPage(page))
                .map(TestCaseRes.Get::of);
    }

    /**
     * 테스트 케이스 생성
     * */
    @Transactional
    public TestCaseRes.Get createTestCase(Long memberId, TestCaseReq.Create req) {
        TestCase testCase = TestCase.builder()
                .assignment(Assignment.ById(req.assignmentId()))
                .member(Member.ById(memberId))
                .input(req.input())
                .output(req.output())
                .description(req.description())
                .build();

        testCaseRepository.save(testCase);

        return TestCaseRes.Get.of(testCase);
    }
}
