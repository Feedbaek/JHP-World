package minskim2.JHP_World.domain.grade.repository;

import minskim2.JHP_World.domain.grade.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Page<Grade> findAllByAssignmentId(Long assignmentId, Pageable of);
}
