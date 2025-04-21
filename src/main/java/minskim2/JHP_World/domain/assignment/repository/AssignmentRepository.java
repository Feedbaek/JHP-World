package minskim2.JHP_World.domain.assignment.repository;

import minskim2.JHP_World.domain.assignment.entity.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Page<Assignment> findAllByLectureId(Long lectureId, Pageable pageable);

    int countByLectureId(Long lectureId);

    List<Assignment> findByTitle(String s);
}
