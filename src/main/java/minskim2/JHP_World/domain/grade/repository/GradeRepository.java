package minskim2.JHP_World.domain.grade.repository;

import minskim2.JHP_World.domain.grade.entity.GradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeHistory, Long> {
}
