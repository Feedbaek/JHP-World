package minskim2.JHP_World.domain.visitor_log.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.visitor_log.entity.QVisitorLog;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VisitorQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Long countTotalVisitors() {
        QVisitorLog visitorLog = QVisitorLog.visitorLog;

        // JPQL FUNCTION('DATE', createdDate) 사용
        StringTemplate dateExpression = Expressions.stringTemplate("FUNCTION('DATE', {0})", visitorLog.createdDate);

        // 날짜별로 고유 IP를 카운트
        return queryFactory
                .select(visitorLog.ip.countDistinct())
                .from(visitorLog)
                .groupBy(visitorLog.ip, dateExpression)
                .fetchOne();
    }
}
