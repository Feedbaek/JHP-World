package minskim2.JHP_World.domain.test_case.repository;

import minskim2.JHP_World.domain.test_case.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    Optional<TestCase> findByMemberId(Long memberId);
}
