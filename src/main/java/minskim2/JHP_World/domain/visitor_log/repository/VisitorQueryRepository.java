package minskim2.JHP_World.domain.visitor_log.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.visitor_log.entity.QVisitorLog;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
@RequiredArgsConstructor
public class VisitorQueryRepository {

    private final JPAQueryFactory queryFactory;

    // 총 방문자 수 조회
    public Long countTotalVisitors() {
        QVisitorLog visitorLog = QVisitorLog.visitorLog;

        // groupBy( DATE(createdDate), ip )
        // → 그룹의 개수 = 날짜 + IP 쌍의 유니크 개수
        var results = queryFactory
                .select(visitorLog.id.count()) // 실제 필드는 어떤 것을 select해도 무방
                .from(visitorLog)
                .groupBy(
                        Expressions.stringTemplate("DATE({0})", visitorLog.createdDate),
                        visitorLog.ip
                )
                .fetch();

        // results = 각 group별 count 값들
        // results.size() = group 별 row 수 = (날짜+IP) 유니크 개수
        return (long) results.size();
    }


    // 오늘 방문자 수 조회
    public Long countTodayVisitors() {
        QVisitorLog visitorLog = QVisitorLog.visitorLog;

        // 오늘 날짜의 00:00:00
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        // 오늘 날짜의 23:59:59.999999 (또는 다음 날 00:00:00 직전)
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        return queryFactory
                .select(visitorLog.ip.countDistinct())
                .from(visitorLog)
                .where(visitorLog.createdDate.between(startOfDay, endOfDay))
                .fetchOne();
    }
}
