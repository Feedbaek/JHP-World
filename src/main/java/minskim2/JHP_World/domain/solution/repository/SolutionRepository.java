package minskim2.JHP_World.domain.solution.repository;

import minskim2.JHP_World.domain.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {

    Optional<Solution> findByIdAndMemberId(Long id, Long memberId);
}
