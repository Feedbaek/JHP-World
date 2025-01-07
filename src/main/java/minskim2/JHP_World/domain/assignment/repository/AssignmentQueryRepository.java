package minskim2.JHP_World.domain.assignment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.assignment.dto.AssignmentQ;
import minskim2.JHP_World.domain.assignment.entity.QAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public Page<AssignmentQ> findListByLectureId(Long lectureId, int offset) {
        // 1. 페이징 처리
        List<AssignmentQ> assignments = queryFactory
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

        // 2. 전체 데이터 개수 조회
        Long totalItems = queryFactory
                .select(assignment.count())
                .from(assignment)
                .where(assignment.lecture.id.eq(lectureId))
                .fetchOne();
        // 2-1. totalItems가 null일 경우 0으로 초기화
        if (totalItems == null) {
            totalItems = 0L;
        }

        // 3. totalPages 계산
        long totalPages = totalItems / ASSIGNMENT_LIST.getSize();
        if (totalItems % ASSIGNMENT_LIST.getSize() != 0) {
            totalPages++;
        }
        // 4. Page 객체로 반환
        return new PageImpl<>(assignments, PageRequest.of(offset, ASSIGNMENT_LIST.getSize()), totalPages);
    }
}
