package minskim2.JHP_World.domain.grade.repository;

import minskim2.JHP_World.domain.grade.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Page<Grade> findAllByAssignmentId(Long assignmentId, Pageable of);

    /**
     * 테스트케이스별 최근 채점 결과 조회 (결과가 존재하는 항목만)
     */
    @Query("select g from Grade g where g.testCase.id = :testCaseId and g.result is not null order by g.createdDate desc")
    List<Grade> findRecentGrades(@Param("testCaseId") Long testCaseId, Pageable pageable);
}
