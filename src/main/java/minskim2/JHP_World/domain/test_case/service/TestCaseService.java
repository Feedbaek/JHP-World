package minskim2.JHP_World.domain.test_case.service;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.TEST_CASE_LIST;

@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    public TestCaseReq.Get getTestCase(Long id) {
        return TestCaseReq.Get.of(testCaseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 테스트 케이스가 존재하지 않습니다.")
        ));
    }

    public List<TestCaseReq.Get> getTestCases(Long assignmentId, int page) {
        return testCaseRepository.findAllByAssignmentId(assignmentId, Pageable.ofSize(TEST_CASE_LIST.getSize()).withPage(page))
                .map(TestCaseReq.Get::of)
                .toList();
    }
}
