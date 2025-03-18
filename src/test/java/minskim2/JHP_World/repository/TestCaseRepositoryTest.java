package minskim2.JHP_World.repository;

import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import utils.TestUtility;

import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.TEST_CASE_LIST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Slf4j(topic = "TestCaseRepositoryTest")
@DisplayName("TestCaseRepository 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TestCaseRepositoryTest {

    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private TestEntityManager em;

    @BeforeEach
    public void setUp() {
        TestUtility.sequenceInit();
    }

    @Test
    @DisplayName("findAllByAssignmentId 메소드 테스트")
    public void findAllByAssignmentId() {
        // given
        List<TestCase> testCaseList = TestUtility.makeTestCaseList(em.getEntityManager(), 100);
        Long assignmentId = testCaseList.getFirst().getAssignment().getId();
        Pageable page = Pageable.ofSize(TEST_CASE_LIST.getSize()).withPage(0);

        // when
        List<TestCase> result = testCaseRepository.findAllByAssignmentId(assignmentId, page).getContent();

        // then
        for (int i=0; i<TEST_CASE_LIST.getSize(); i++) {
            TestCase testCase = testCaseList.get(i);
            TestCase resultTestCase = result.get(i);
            log.info("resultTestCaseId: {}, testCaseId: {}", resultTestCase.getId(), testCase.getId());
            assertThat(resultTestCase.getId()).isEqualTo(testCase.getId());
        }
    }
}
