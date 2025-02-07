package minskim2.JHP_World.domain.test_case.repository;

import minskim2.JHP_World.domain.test_case.entity.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    Optional<TestCase> findByMemberId(Long memberId);

    @EntityGraph(attributePaths = {"assignment", "member"})
    Page<TestCase> findAllByAssignmentId(Long assignmentId, Pageable pageable);
}
