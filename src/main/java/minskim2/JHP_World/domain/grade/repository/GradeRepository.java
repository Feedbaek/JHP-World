package minskim2.JHP_World.domain.grade.repository;

import minskim2.JHP_World.domain.grade.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Page<Grade> findAllByAssignmentId(Long assignmentId, Pageable of);

    /** 최근 테스트 결과를 최신순으로 조회 */
    @Query("select g from Grade g where g.assignment.id = :assignmentId order by g.createdDate desc")
    List<Grade> findLatestByAssignmentId(@Param("assignmentId") Long assignmentId);
}
