package minskim2.JHP_World.domain.assignment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentQ;
import minskim2.JHP_World.domain.assignment.entity.QAssignment;
import org.springframework.stereotype.Repository;

import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.ASSIGNMENT_LIST;

@Repository
@RequiredArgsConstructor
public class AssignmentQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QAssignment assignment = QAssignment.assignment;

    /**
     * LectureId로 Assignment List 조회
     * */
    public List<AssignmentQ> findListByLectureId(Long lectureId, int offset) {
        return queryFactory
                .select(
                        Projections.fields(AssignmentQ.class,
                                assignment.id,
                                assignment.title
                        )
                )
                .from(assignment)
                .where(assignment.lecture.id.eq(lectureId))
                .orderBy(assignment.createdDate.desc())
                .limit(ASSIGNMENT_LIST.getSize())
                .offset(offset)
                .fetch();
    }
}
