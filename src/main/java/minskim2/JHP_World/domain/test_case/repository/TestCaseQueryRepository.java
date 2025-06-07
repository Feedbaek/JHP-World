package minskim2.JHP_World.domain.test_case.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.grade.entity.QGrade;
import minskim2.JHP_World.domain.recommendation.entity.QRecommendation;
import minskim2.JHP_World.domain.test_case.dto.TestCaseQ;
import minskim2.JHP_World.domain.test_case.entity.QTestCase;
import minskim2.JHP_World.domain.member.entity.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static minskim2.JHP_World.global.enums.SizeEnum.TEST_CASE_LIST;

@Repository
@RequiredArgsConstructor
public class TestCaseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<TestCaseQ> findList(Long assignmentId, String username, String sort, int page) {
        QTestCase testCase = QTestCase.testCase;
        QGrade grade = QGrade.grade;
        QRecommendation recommendation = QRecommendation.recommendation;
        QMember member = QMember.member;

        NumberExpression<Long> runCount = grade.id.countDistinct();
        NumberExpression<Long> recommendCount = recommendation.id.countDistinct();
        NumberExpression<Long> commentCount = Expressions.asNumber(0L);

        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if ("run".equals(sort)) {
            orders.add(runCount.desc());
        } else if ("recommend".equals(sort)) {
            orders.add(recommendCount.desc());
        } else if ("comment".equals(sort)) {
            orders.add(commentCount.desc());
        } else { // recent
            orders.add(testCase.createdDate.desc());
        }

        var query = queryFactory
                .select(Projections.fields(TestCaseQ.class,
                        testCase.id,
                        testCase.assignment.id.as("assignmentId"),
                        testCase.assignment.title.as("assignmentTitle"),
                        testCase.member.id.as("memberId"),
                        testCase.member.name.as("member"),
                        testCase.description,
                        runCount.as("runCount"),
                        commentCount.as("commentCount"),
                        recommendCount.as("recommendCount"),
                        testCase.createdDate
                ))
                .from(testCase)
                .leftJoin(testCase.member, member)
                .leftJoin(grade).on(grade.testCase.eq(testCase))
                .leftJoin(recommendation).on(recommendation.testCase.eq(testCase))
                .where(testCase.assignment.id.eq(assignmentId)
                        .and(testCase.isPublic.isTrue()))
                .groupBy(testCase.id);

        if (StringUtils.hasText(username)) {
            query.where(member.name.contains(username));
        }

        orders.forEach(query::orderBy);

        List<TestCaseQ> content = query
                .offset((long) page * TEST_CASE_LIST.getSize())
                .limit(TEST_CASE_LIST.getSize())
                .fetch();

        var countQuery = queryFactory
                .select(testCase.count())
                .from(testCase)
                .leftJoin(testCase.member, member)
                .where(testCase.assignment.id.eq(assignmentId)
                        .and(testCase.isPublic.isTrue()));
        if (StringUtils.hasText(username)) {
            countQuery.where(member.name.contains(username));
        }

        Long total = countQuery.fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(content, PageRequest.of(page, TEST_CASE_LIST.getSize()), total);
    }
}
